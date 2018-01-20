/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.gda.pg.eti.jg.be_projekt;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author user
 */

@XmlType(name = "recommandation")
@XmlAccessorType(XmlAccessType.NONE)
@XmlRootElement(name = "product")
public class ProductData {
    
    public ProductData(){}
    
    public ProductData(String name, String link, String photo, String price){
        this.name = name;
        this.price = price;
        this.productLink = link;
        this.productPhotoLink = photo;
    }
    
    @XmlAttribute(name = "name")
    public String name;
    
    @XmlAttribute(name = "link")
    public String productLink;
    
    @XmlAttribute(name = "photo")
    public String productPhotoLink;
    
    @XmlAttribute(name = "price")
    public String price;
}
