/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Products;
import Entities.ProductsJpaController;
import Entities.Stores;
import Entities.StoresJpaController;
import static controller.LoginBean.adminName;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.List;
import javax.faces.bean.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Part;
import model.ProductQueary;
import model.StoreBuilder;
import model.StoreQueary;

@ManagedBean
@ViewScoped
public class BuildProductBean {

    private int stock;
    private int storeId;                   // th current store id
    private String storeName;
    private String productPhoto;
    private String description;
    private int price;
    private String productName;
    private int productId;
    private Part file;
    private ProductsJpaController productsJpaCtrl;
    private List<Products> productsList;

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public Part getFile() {
        return file;
    }

    public void setFile(Part file) {
        this.file = file;
    }

    public BuildProductBean() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        productsJpaCtrl = new ProductsJpaController(emf);
        this.storeId = BuildStoreBean.currentStoreId;
        this.storeName = BuildStoreBean.currentStoreName;
        ProductQueary productQueary = new ProductQueary();
        productsList = productQueary.getProductsByStoreId(this.storeId);
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    public int getStoreId() {
        return storeId;
    }

    public String getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(String productPhoto) {
        this.productPhoto = productPhoto;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public ProductsJpaController getProductsJpaCtrl() {
        return productsJpaCtrl;
    }

    public void setProductsJpaCtrl(ProductsJpaController productsJpaCtrl) {
        this.productsJpaCtrl = productsJpaCtrl;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public void setStoreId(int StoreId) {
        this.storeId = StoreId;
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

        if (this.productId < 1) {
            return null;
        }

        String dirPath = "C:\\onlineShopping\\" + BuildStoreBean.currentStoreName;
        this.productPhoto = this.productId + "product.jpg";

        Products newProduct = new Products(this.productId, this.productName, this.price, this.stock, this.productPhoto, BuildStoreBean.currentStoreId, this.description);

        storeDB.save(newProduct);
        try (InputStream input = file.getInputStream()) {
            Files.copy(input, new File(dirPath, this.productPhoto).toPath());
        } catch (IOException e) {
            // Show faces message?
        }

        return null;
    }

}
