/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Products;
import Entities.Stores;
import static controller.LoginBean.adminName;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import javax.faces.bean.*;
import javax.servlet.http.Part;
import model.StoreBuilder;

@ManagedBean
@ViewScoped
public class BuildProductBean {

    private int stock;
    private int StoreId;
    private String productPhoto;
    private String description;
    private int price;
    private String productName;
    private int productId;
    private Part file;
    
    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStoreId() {
        return StoreId;
    }

    public void setStoreId(int StoreId) {
        this.StoreId = StoreId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String ProductName) {
        this.productName = ProductName;
    }

    public String createNewProduct() {
        if (this.productName == null || this.description == null || this.stock < 1 || this.price < 1 || this.file == null) {
            return null;
        }
        StoreBuilder storeDB = new StoreBuilder();
        this.productId = storeDB.createProductId() + 1;
        
        if(this.productId < 1){
            return null;
        }
        
        String dirPath = "C:\\work space\\" + BuildStoreBean.storeId;
        this.productPhoto = this.productId +  "product.jpg";

        Products newProduct = new Products(this.productId, this.productName, this.price, this.stock , this.productPhoto,  BuildStoreBean.storeId, this.description);

        storeDB.save(newProduct);
           try (InputStream input = file.getInputStream()) {
            Files.copy(input, new File(dirPath, this.productPhoto).toPath());
        } catch (IOException e) {
            // Show faces message?
        }
        
        return null;
    }
    
}
