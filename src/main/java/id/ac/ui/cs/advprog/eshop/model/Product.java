package id.ac.ui.cs.advprog.eshop.model;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Product {
    private String productId;
    private String productName;
    private int productQuantity;
    static int staticId;

    static public String setNewId() {
        staticId++;
        return Integer.toString(staticId);
    }

    public boolean editProduct(Product edittedProduct){
        if(edittedProduct.productQuantity >= 0){
            this.productName = edittedProduct.productName;
            this.productQuantity = edittedProduct.productQuantity;
            return true;
        }else{
            return false;
        }
    }
}
