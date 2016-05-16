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
import Entities.exceptions.NonexistentEntityException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.*;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.http.Part;
import model.ProductQueary;
import model.StoreBuilder;
import model.StoreQueary;

@ManagedBean
@RequestScoped
public class BuildProductBean {

    @ManagedProperty(value = "#{param.selectedProductId}")
    private int selectedProductId;
    
    @ManagedProperty(value = "#{param.selectedCategory}")
    private String selectedCategory;
    private static int currentProductId;
    private int stock;
    private int storeId;                   // the current store id
    private String storeName;
    private String productPhoto;
    private String description;
    private int price;
    private String productName;
    private int productId;
    private Part file;
    private ProductsJpaController productsJpaCtrl;
    private List<Products> productsList;
    private String[] categories;
    private String category;
    private final String ALL = "ALL";

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public int getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(int selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

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

        buildCategories(productsList);
    }
    
    

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
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

    private void buildCategories(List<Products> productsList) {
        if (productsList != null && productsList.size() > 0) {
            List<String> categoryList = new ArrayList();
            categoryList.add(ALL);  // first category option will be all products
            
            for (Products product : productsList) {
                if (product.getCategory() != null  && product.getCategory().length() > 1 &&  !categoryList.contains(product.getCategory())) {
                    categoryList.add(product.getCategory());
                }
            }

            this.categories = categoryList.toArray(new String[0]);
        } else {
            this.categories = null;
        }
    }

    public void sortByCategory() {
        ProductQueary productQueary = new ProductQueary();
        this.productsList = productQueary.getProductsByStoreId(this.storeId);
        if (this.selectedCategory.equals(ALL)) {
            return;
        }

        List<Products> tempProdList = new ArrayList();
        for (Products prod : productsList) {
            if (prod.getCategory() != null && prod.getCategory().equals( this.selectedCategory)) {
                tempProdList.add(prod);
            }
        }
        this.productsList = tempProdList;
    }

    public String createNewProduct() {
        if (this.productName == null || this.description == null || this.stock < 1 || this.price < 1 || this.file == null) {
            return null;
        }
        StoreBuilder storeDB = new StoreBuilder();
        this.productId = storeDB.createProductId();

        String dirPath = "C:\\onlineShopping\\" + BuildStoreBean.currentStoreId;
        this.productPhoto = this.productId + "product.jpg";

        Products newProduct = new Products(this.productId, this.productName, this.price, this.stock, this.productPhoto, BuildStoreBean.currentStoreId, this.description);

        if (this.category.length() > 1) {
            newProduct.setCategory(category);
        }

        storeDB.save(newProduct);
        productsList.add(newProduct);
        buildCategories(productsList);

        try (InputStream input = file.getInputStream()) {
            Files.copy(input, new File(dirPath, this.productPhoto).toPath());
        } catch (IOException e) {
            // Show faces message?
        }

        this.productName = "";
        this.description = "";
        this.stock = 0;
        this.price = 0;
        this.file = null;
        this.category = null;
        return null;
    }

    public void updateProduct() {

        Products productToUpdate = getProducteById(this.currentProductId);
        if (this.productName.length() > 1) {
            productToUpdate.setProductName(this.productName);
        }
        if (this.description.length() > 1) {
            productToUpdate.setDescription(this.description);
        }
        if (this.stock > 1) {
            productToUpdate.setStock(this.stock);
        }
        if (this.price > 1) {
            productToUpdate.setPrice(this.price);
        }
        
         if (this.category.length() > 1) {
            productToUpdate.setCategory(this.category);
        }

        try {
            productsJpaCtrl.edit(productToUpdate);
        } catch (Exception ex) {
            Logger.getLogger(Stores.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (this.file != null) {
            String dirPath = "C:\\onlineShopping\\" + this.storeId;
            String imgPath = dirPath + "\\" + productToUpdate.getPhoto();
            File oldImage = new File(imgPath);     // deleting old image
            oldImage.delete();

            try (InputStream input = this.file.getInputStream()) {
                Files.copy(input, new File(dirPath, "\\" + productToUpdate.getPhoto()).toPath());
            } catch (IOException e) {}
        }
        buildCategories(productsList);
    }

    public void deleteProduct() throws NonexistentEntityException {
        int _selectedProductId = this.selectedProductId;
        Products productToRemove = getProducteById(_selectedProductId);

        String dirPath = "C:\\onlineShopping\\" + this.storeId;
        String fullImgPath = dirPath + "\\" + productToRemove.getPhoto();
        deleteImgFromDir(fullImgPath);

        productsJpaCtrl.destroy(_selectedProductId);
        Products product = getProducteById(_selectedProductId);
        productsList.remove(product);
        buildCategories(productsList);
    }

    private void deleteImgFromDir(String fullPath) {
        File oldImage = new File(fullPath);
        oldImage.delete();
    }

    private Products getProducteById(int id) {
        for (Products tmpProduct : productsList) {
            if (tmpProduct.getProductId() == id) {
                return tmpProduct;
            }
        }
        return null;
    }

    public void saveProductId() {
        this.currentProductId = this.selectedProductId;
    }
}
