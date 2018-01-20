/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.jg.be_projekt;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import static java.nio.charset.StandardCharsets.*;

/**
 *
 * @author user
 */
public class PrestaDB {
    final String myDriver = "org.gjt.mm.mysql.Driver";
    final String myUrl = "jdbc:mysql://localhost:3306/prestashop?characterEncoding=utf8";
    String domainAddress;
    Connection conn = null;
    
    PrestaDB(){
          // create our mysql database connection
          try{
            Class.forName(myDriver);
            conn = DriverManager.getConnection(myUrl, "mahoutapi", "admin");
          }catch(Exception e){
              e.printStackTrace();
              System.err.println("Can not connect to database!");
          }
          domainAddress = getDomainAddres();
    }
    
    private String getDomainAddres(){
        String da = null;
        try{
            String query = "SELECT domain FROM ps_shop_url"; 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            rs.next();
            da = rs.getString(1);
            System.out.println("Domain = " + da);
             st.close();
        }
        catch (Exception e){
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
        return da;
    }
    
    private String makeQueryForRecommendations(List<Integer> id_products){
        String query 
                = "select p.id_product, il.legend, l.link_rewrite, p.price, "
                + "cl.link_rewrite, i.id_image\n" +
"	from ps_product as p\n" +
"		join ps_product_lang as l on p.id_product = l.id_product\n" +
"		join ps_image as i on p.id_product = i.id_product\n" +
"		join ps_image_lang as il on i.id_image = il.id_image\n" +
"		join ps_category_product as cp on p.id_product = cp.id_product\n" +
"		join ps_category_lang as cl on cp.id_category = cl.id_category "
                + "where p.id_product in (";
        
        for (int i = 0; i < id_products.size() - 1; i++)
            query += id_products.get(i) + ", ";
        query += id_products.get(id_products.size() - 1);
        query += ")";
            
        return query;
    }
    
    public List<ProductData> getRecommendedProducts(List<Integer> id_products){ 
        List<ProductData> products = new ArrayList<>();
        
        try{
            String query = makeQueryForRecommendations(id_products); 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
               ProductData p = new ProductData(); 
                
               p.name = rs.getString(2);
               Double temp = (Double)(rs.getDouble(4) * 1.23);
               
               p.price = String.format("%.2f", temp);
               
               String category = rs.getString(5);
               int id_product = rs.getInt(1);
               String nameLink = rs.getString(3);
               
               p.productLink  = "http://" + domainAddress + "/" +
                       category + "/" + id_product + "-" + nameLink + ".html";

               int id_image = rs.getInt(6);
               p.productPhotoLink = "http://" + domainAddress + "/" + id_image +
                       "-large_default/" + nameLink + ".jpg";
               products.add(p);
             }
             st.close();
        }
        catch (Exception e){
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
        
        return products;
    }
    
    public List<Ratings> getRatings(){
        List<Ratings> ratings = new ArrayList<>();
        
            try{
            String query = "SELECT FK_customer, FK_product, grade FROM ps_customer_rating"; 
            
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);

            while (rs.next()){
               Ratings r = new Ratings();
                
               r.id_customer = rs.getLong(1);
               r.id_product = rs.getLong(2);
               r.grade = rs.getDouble(3);
                
               ratings.add(r);
             }
             st.close();
        }
        catch (Exception e){
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
        
        return ratings;
    }
    
    private String makeQueryToGetIdCustomer(String firstName, String lastName){
        return "SELECT id_customer FROM ps_customer WHERE firstname = '" +
                firstName + "' and lastname = '" + lastName + "'";
    }
    
    public int getIdCustomer(String firstName, String lastName){
        int ID_customer = -1;
        
        try{
            String query = makeQueryToGetIdCustomer(firstName, lastName); 
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery(query);
            
            rs.next();
            ID_customer = rs.getInt(1);
            System.out.println("Customer = " + firstName + " " + lastName + ", ID = " + ID_customer);
            st.close();
        }
        catch (Exception e){
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
        
        return ID_customer;
    }
    
    private String makeQueryToGetIdProduct(String productName){
        return "SELECT id_product FROM ps_product_lang WHERE name = '" + 
                productName + "'";
    }
    
    public int getIdProduct(String productName){
        int ID_product = -1;

        try{
            String query = makeQueryToGetIdProduct(productName); 
            Statement st = conn.createStatement();

            ResultSet rs = st.executeQuery(query);
            
            rs.next();
            ID_product = rs.getInt(1);
            System.out.println("Product ID = " + ID_product);
            st.close();
        }
        catch (Exception e){
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
        
        return ID_product;
    }
    
    private String makeQueryToSaveRatings(int id_customer, int id_product, int grade){
        return "INSERT INTO ps_customer_rating (FK_customer, FK_product, grade) VALUES (" 
                + id_customer + ", " + id_product + ", " + grade + ")"; 
    }
    
    public void saveRagingsInDB(int id_customer, int id_product, int grade){
        
        try{
            String query = makeQueryToSaveRatings(id_customer, id_product, grade); 
            Statement st = conn.createStatement();
            st.execute(query);
            
            st.close();
        }
        catch (Exception e){
          System.err.println("Got an exception! ");
          System.err.println(e.getMessage());
        }
    }
}
