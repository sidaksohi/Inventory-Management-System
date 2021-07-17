package Model;

import java.util.ArrayList;
import java.util.List;
import javafx.collections.*;
import static jdk.nashorn.internal.objects.NativeString.toLowerCase;

/**
 *
 * @author Sidak
 */
public class Inventory {
    private ArrayList<Part> allParts;
    private ArrayList<Product> allProducts;
    private ArrayList<Product> lookupProductResult = new ArrayList<Product>();
    private ArrayList<Part> lookupPartResult = new ArrayList<Part>();
    
    public Inventory() {
        allParts = new ArrayList<>();
        allProducts = new ArrayList<>();
    }
    
    public void addPart(Part partToAdd){
        if(partToAdd != null){
            this.allParts.add(partToAdd);
        }
    }
    
    public void addProduct(Product productToAdd){
        if(productToAdd != null){
            this.allProducts.add(productToAdd);
        }
    }
    
    public ObservableList<Part> lookupPart (String partName){
        if(!allParts.isEmpty()){
            lookupPartResult.clear();
            for(int i = 0; i < allParts.size(); i++){
                if((allParts.get(i).getName()).contains(partName) == true || (Integer.toString(allParts.get(i).getID())).contains(partName) == true){
                    lookupPartResult.add(allParts.get(i));
                }
            }
        return FXCollections.observableList(lookupPartResult);  
        }else return null;
    }
    
    public ObservableList<Product> lookupProduct (String productName) {
         if(!allProducts.isEmpty()){
            lookupProductResult.clear();
            for(int i = 0; i < allProducts.size(); i++){
                if((toLowerCase(allProducts.get(i).getName())).contains(toLowerCase(productName)) == true || (Integer.toString(allProducts.get(i).getID())).contains(productName) == true){
                    lookupProductResult.add(allProducts.get(i));
                }
            }
            return FXCollections.observableList(lookupProductResult);
        }
        return null;       
    }
    
    public void updatePart(int index, Part selectedPart){
        allParts.set(index, selectedPart);
    }
    
    public void updateProduct(int index, Product selectedProduct){
        allProducts.set(index, selectedProduct);
    }
    
    public boolean deletePart(int selectedIndex){
        if(selectedIndex >= 0){
                allParts.remove(selectedIndex);
            } else {
                return false;
            }     
        return true;
    }
    
    public boolean deleteProduct(int selectedIndex){
        if(selectedIndex >= 0){
                allProducts.remove(selectedIndex);
            } else {
                return false;
            }     
        return true;
    }
    
    public ObservableList<Part> getAllParts(){
       return FXCollections.observableList(allParts);
    }
    
    public ObservableList<Product> getAllProducts(){
        return FXCollections.observableList(allProducts);
    }






}
