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
//    private final List<ProductData> recommandations;
    
    public RecommandationContext(){
//	recommandations = new ArrayList<ProductData>();
//	recommandations.add(new ProductData("BETON KOMÓRKOWY BIAŁY H+H", "79-beton-komorkowy-bialy-hh.html", "http://localhost/97-large_default/beton-komorkowy-bialy-hh.jpg", "5.29"));
//	recommandations.add(new ProductData("AKRYL DO PŁYT G-K 300 DEN BRAVEN", "637-akryl-do-plyt-g-k-300-den-braven.html", "http://localhost/655-large_default/akryl-do-plyt-g-k-300-den-braven.jpg","12.24"));
//	recommandations.add(new ProductData("AKRYL EXTRA ZEWNĘTRZNY 300 DEN BRAVEN", "556-akryl-extra-zewnetrzny-300-den-braven.html", "http://localhost/574-large_default/akryl-extra-zewnetrzny-300-den-braven.jpg","12.24"));
//	recommandations.add(new ProductData("DACHÓWKA BITUMICZNA ROLOWANA DZIAŁKOWIEC KARPIÓWKA 3D 11 ICOPAL", "752-dachowka-bitumiczna-rolowana-dzialkowiec-karpiowka-3d-11-icopal.html", "http://localhost/770-large_default/dachowka-bitumiczna-rolowana-dzialkowiec-karpiowka-3d-11-icopal.jpg","216.70"));
//	recommandations.add(new ProductData("FARBA NA DACH CYNKOBOND DEN BRAVEN", "1107-farba-na-dach-cynkobond-den-braven.html", "http://localhost/1125-large_default/farba-na-dach-cynkobond-den-braven.jpg","244.77"));
/*	recommandations.add(new ProductData("ŁĄCZNIK RYNNOWY ZEWNĘTRZNY G 80 SCALA PLASTICS", "1040-lacznik-rynnowy-zewnetrzny-g-80-scala-plastics.html", "http://localhost/1058-large_default/lacznik-rynnowy-zewnetrzny-g-80-scala-plastics.jpg",21.87f));
	recommandations.add(new ProductData("ZŁĄCZKA RYNNY G 125 SCALA PLASTICS", "927-zlaczka-rynny-g-125-scala-plastics.html", "http://localhost/945-large_default/zlaczka-rynny-g-125-scala-plastics.jpg",20.91f));
	recommandations.add(new ProductData("EMULSJA GRUNTUJĄCA DL - 80 BUTELKA CEKOL", "298-emulsja-gruntujaca-dl-80-butelka-cekol.html", "http://localhost/316-large_default/emulsja-gruntujaca-dl-80-butelka-cekol.jpg",15.53f));
	recommandations.add(new ProductData("FARBA GRUNTUJĄCA EKOGRUNT KANISTER BETONDUR", "284-farba-gruntujaca-ekogrunt-kanister-betondur.html", "http://localhost/302-large_default/farba-gruntujaca-ekogrunt-kanister-betondur.jpg",24.45f));
	recommandations.add(new ProductData("GRUNT BETONKONTAKT NR 15 7,5 KG ARTISAN", "316-grunt-betonkontakt-nr-15-75-kg-artisan.html", "http://localhost/334-large_default/grunt-betonkontakt-nr-15-75-kg-artisan.jpg",41.81f));
*/	
    }
    
//    public List<ProductData> findAllRecommandations(){
//	return recommandations;
//    }
    
    public List<ProductData> getReccommendationsForCustomer(String firstName, String lastName){
        int id_customer = db.getIdCustomer(firstName, lastName);

        // tutaj wrzucam to do Mahout-a (dla którego klienta chcę dostać rekomendacje)
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
