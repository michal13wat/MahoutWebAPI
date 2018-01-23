/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mahout;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.model.file.FileDataModel;
import org.apache.mahout.cf.taste.impl.neighborhood.ThresholdUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericItemBasedRecommender;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.EuclideanDistanceSimilarity;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.recommender.Recommender;
import org.apache.mahout.cf.taste.recommender.UserBasedRecommender;
import org.apache.mahout.cf.taste.similarity.ItemSimilarity;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import pl.gda.pg.eti.jg.be_projekt.Ratings;
import static java.lang.Math.toIntExact;

/**
 *
 * @author user
 */
public class MahoutUtil {
    
        public List<Integer> findRecommendationForUser(int id_user, List<Ratings> ratings) {
                      
            String filePath = System.getProperty("user.home") + "/" + TEMP_FILE_NAME;
            
            List<Integer> idRecommendedProducts = new ArrayList<>();
            
            try (Writer writer = new BufferedWriter(new OutputStreamWriter(
              new FileOutputStream(filePath), "utf-8"))) {
                for(Ratings r : ratings)
                    writer.write(r.id_customer + "," + r.id_product + "," + String.format("%.1f", r.grade) + "\n");
             }catch(Exception e){
                 e.printStackTrace();
             }
            
            try{
                DataModel model = new FileDataModel(new File(filePath));
                ItemSimilarity itemSimilarity = new EuclideanDistanceSimilarity (model);
                Recommender itemRecommender = new GenericItemBasedRecommender(model,itemSimilarity);
                
                List<RecommendedItem> itemRecommendations = itemRecommender.recommend(id_user, 5);
                for (RecommendedItem itemRecommendation : itemRecommendations) {
                    System.out.println("ID recommended product = " 
                            + toIntExact(itemRecommendation.getItemID()));
                    idRecommendedProducts.add(toIntExact(itemRecommendation.getItemID()));
                }
            }catch(Exception e){
                e.printStackTrace();
            }

            return idRecommendedProducts;
        }
        
        final private String TEMP_FILE_NAME = "data.csv";
}
