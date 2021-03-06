/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Products;
import Entities.ProductsJpaController;
import Entities.UsersCart;
import Entities.UsersCartJpaController;
import Entities.UsersCartPK;
import Entities.exceptions.NonexistentEntityException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import model.ProductQueary;
import model.UserCartQuary;

@ManagedBean
@RequestScoped
public class BuyProductCtrl {

    @ManagedProperty(value = "#{param.selectedProductId}")
    private int selectedProductId;

    @ManagedProperty(value = "#{param.selectedCategory}")
    public String selectedCategory;

    private static String currentSelectedCategory;   // to save current category user hase been selected (after "add to cart" the selectedCategory become null...)
    private static int currentProductId;
    private List<Products> productsList;
    private int storeId;
    private String storeName;
    private UsersCart userCart;
    public static int cost;
    private String userName;
    UsersCartJpaController userCartCtrl;
    ProductsJpaController productCtrl;
    UserCartQuary userCartDB;
    List<UsersCart> cartList;
    private String[] categories;
    private String category;
    private final String ALL = "ALL";

    public BuyProductCtrl() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("online_shoppingPU");
        userCartCtrl = new UsersCartJpaController(emf);
        productCtrl = new ProductsJpaController(emf);
        this.storeId = BuildStoreBean.currentStoreId;
        this.storeName = BuildStoreBean.currentStoreName;
        ProductQueary productQueary = new ProductQueary();
        productsList = productQueary.getProductsWithQuantity(this.storeId);
        buildCategories(productsList);

        userCartDB = new UserCartQuary();

        this.cost = 0;

        if (UserBean.userName != null && UserBean.userName.length() > 3) {
            this.userName = UserBean.userName;
            cartList = userCartDB.getUserCart(this.userName);
            for (UsersCart product : cartList) {
                this.cost += product.getQuantity() * product.getProductPrice();
            }
        }
    }

    public String getSelectedCategory() {
        return selectedCategory;
    }

    public void setSelectedCategory(String selectedCategory) {
        this.selectedCategory = selectedCategory;
    }

    public String[] getCategories() {
        return categories;
    }

    public void setCategories(String[] categories) {
        this.categories = categories;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * ***************** setters & getters ********************
     */
    public int getSelectedProductId() {
        return selectedProductId;
    }

    public void setSelectedProductId(int selectedProductId) {
        this.selectedProductId = selectedProductId;
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    public int getStoreId() {
        return storeId;
    }

    public void setStoreId(int storeId) {
        this.storeId = storeId;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    /**
     * ***************** setters & getters ********************
     */
    private void buildCategories(List<Products> productsList) {
        if (productsList != null && productsList.size() > 0) {
            List<String> categoryList = new ArrayList();
            categoryList.add(ALL);  // first category option will be all products/

            for (Products product : productsList) {
                if (addCategoryToCategoryList(categoryList, product)) {

                    categoryList.add(product.getCategory());
                }
            }

            this.categories = categoryList.toArray(new String[0]);
        }
    }

    private boolean addCategoryToCategoryList(List<String> categoryList, Products product) {
        return product.getCategory() != null
                && product.getCategory().length() > 1
                && !categoryList.contains(product.getCategory())
                && product.getStock() > 0;
    }

    public void sortByCategory() {
        ProductQueary productQueary = new ProductQueary();
        List<Products> tempProductsList = productQueary.getProductsByStoreId(this.storeId);
        if (this.selectedCategory.equals(ALL)) {
            return;
        }

        this.currentSelectedCategory = this.selectedCategory;
        List<Products> tempProdList = new ArrayList();
        for (Products prod : tempProductsList) {
            if (prod.getStock() > 0 && prod.getCategory() != null && prod.getCategory().equals(this.selectedCategory)) {
                tempProdList.add(prod);
            }
        }
        if (tempProdList.size() > 0) {
            this.productsList = tempProdList;
        } else {
            this.currentSelectedCategory = ALL;
        }

    }

    public void addToCart() throws NonexistentEntityException, Exception {
        int _selectedProdId = this.selectedProductId;
        List<UsersCart> cartList = null;
        userCartDB = new UserCartQuary();
        // return product list that must contain exactly one element
        Products product = new ProductQueary().getProductByProductId(_selectedProdId).get(0);   // get product object
        if (HomeCtrl.userName != null) {                                        // user logged in...
            cartList = userCartDB.getUserProduct(HomeCtrl.userName, _selectedProdId);
        } else {
            return;
        }

        if (cartList.size() > 0) {     // the user already bought this product
            cartList.get(0).setQuantity(cartList.get(0).getQuantity() + 1);    // quantitiy++
            try {
                userCartCtrl.edit(cartList.get(0));
            } catch (Exception ex) {
                Logger.getLogger(BuyProductCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            String adminName = userCartDB.getAdminName(product.getStoreId());
            UsersCartPK userCartPk = new UsersCartPK(HomeCtrl.userName, _selectedProdId);
            UsersCart userCart = new UsersCart(userCartPk, 1, product.getPrice(), product.getStoreId(), product.getProductName(), this.storeName, adminName);
            try {

                userCartCtrl.create(userCart);
            } catch (Exception ex) {
                Logger.getLogger(BuyProductCtrl.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        this.cost += product.getPrice();
        int newStock = product.getStock() - 1;
        if (newStock < 1) {
            productsList.remove(product);
        }
        product.setStock(newStock);
        productCtrl.edit(product);
        buildCategories(productsList);
        this.selectedCategory = this.currentSelectedCategory;
        if (this.selectedCategory != null) {
            sortByCategory();
        }

    }

    public String buyProduct() throws Exception {
        this.addToCart();
        return "checkout page";
    }
}
