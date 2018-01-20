/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.jg.resources;

import java.util.*;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import pl.gda.pg.eti.jg.be_projekt.ProductData;
import pl.gda.pg.eti.jg.be_projekt.RecommandationContext;
/**
 *
 * @author irrigon
 */
@Path("/recommandations")
public class RecommandationResource {
    public static final String RECOMMENDATION_CONTEXT = "recommendationContext";
    
    @Context
    ServletContext context;
    
    @Context
    HttpServletRequest request;
    
    @Context
    HttpServletResponse response;
    
//    @GET
//    @Path("list")
//    @Produces(MediaType.APPLICATION_JSON)
//    public List<Recommandation> listRecommandations(){
//	return getContext().findAllRecommandations();
//    }
//    
//    @POST
//    @Path("clientName")
//    public void recaiveClientName(@FormParam("name") String name){
//        System.out.println("post: " + name);
//    }
//    }
//    
    @GET
    @Path("customer")
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductData> getRecommendations(@DefaultValue("###") 
        @QueryParam("fn") String firstName, @DefaultValue("###") 
        @QueryParam("ln") String lastName){
        System.out.println("customer name = " + firstName);
        //return getContext().getReccommendationsForCustomer(firstName, lastName);
//        return getContext().findAllRecommandations();
        return getContext().getReccommendationsForCustomer(firstName, lastName);
    }
    
   /* @GET
    @Path("customer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getRecommendations(@DefaultValue("###") 
        @QueryParam("fn") String firstName, @DefaultValue("###") 
        @QueryParam("ln") String lastName){
        System.out.println("customer name = " + firstName);
        //return getContext().getReccommendationsForCustomer(firstName, lastName);
//        return getContext().findAllRecommandations();
        return getContext().getReccommendationsForCustomer(firstName, lastName);
    }*/
    
    @GET
    @Path("rate")
    public Response getRate(@DefaultValue("###") 
        @QueryParam("name") String name, @DefaultValue("###") 
        @QueryParam("fn") String fn, @DefaultValue("###") 
        @QueryParam("ln") String ln, @DefaultValue("###") 
        @QueryParam("rate") String rate){
//        System.out.println("product name = " + name + " rate: " + rate + " client: " +
//                fn + " " + ln);
            String productName = name;
            productName = productName.substring(1,name.length()-1);
            String client = fn + " " + ln;
            Integer rating = Integer.parseInt(rate);
        System.out.println("product name = " + productName + " rate: " + rating + " client: " +
                client);
        getContext().saveRatingInDB(fn, ln, productName, rating);
        
        return Response
            .status(204)
            .header("Access-Control-Allow-Origin", "*")
            .header("Access-Control-Allow-Headers", "origin, content-type, accept, authorization")
            .header("Access-Control-Allow-Credentials", "true")
            .header("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD")
            .header("Access-Control-Max-Age", "1209600")
            .build();
    }
    
    private RecommandationContext getContext(){
	RecommandationContext context = (RecommandationContext) this.context.getAttribute(RECOMMENDATION_CONTEXT);
	if(context == null){
	    context = new RecommandationContext();
	    this.context.setAttribute(RECOMMENDATION_CONTEXT, context);
	}
	return context;
    }
}
