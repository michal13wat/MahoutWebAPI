/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.jg.be_projekt;

import java.util.*;
import mahout.MahoutUtil;

/**
 *
 * @author irrigon
 */
public class RecommandationContext {
    
    public RecommandationContext(){}
    
    public List<ProductData> getReccommendationsForCustomer(String firstName, String lastName){
        int id_customer = db.getIdCustomer(firstName, lastName);
        MahoutUtil m = new MahoutUtil();

        List<Integer> id_products = 
                m.findRecommendationForUser(id_customer, db.getRatings());

        return db.getRecommendedProducts(id_products);
    }
    
    public void saveRatingInDB(String firstName, String lastName, 
            String productName, int grade){
        int id_customer = db.getIdCustomer(firstName, lastName);
        int id_product = db.getIdProduct(productName);
        db.saveRagingsInDB(id_customer, id_product, grade);
        
    }
    
    private final PrestaDB db = new PrestaDB();
}
