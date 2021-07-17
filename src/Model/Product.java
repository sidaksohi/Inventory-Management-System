package Model;

import java.util.ArrayList;
import javafx.collections.*;
import javafx.collections.FXCollections;

/**
 *
 * @author Sidak
 */
public class Product{
    private ArrayList<Part> associatedParts;
    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;
    
    public Product(int id, String name, double price, int stock, int min, int max){
        associatedParts = new ArrayList<Part>();
        
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    } 
    
    public Product(int id, String name, double price, int stock, int min, int max, ArrayList<Part> associatedParts){
        this.associatedParts = associatedParts;
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
    } 
    
    public void setID(int i){
        this.id = i;
    }
    
    public void setName(String n){
        this.name = n;
    }
    
    public void setPrice(double p){
        this.price = p;
    }
    
    public void setStock(int s){
        this.stock = s;
    }
    
    public void setMin(int mi){
        this.min = mi;
    }
    
    public void setMax(int ma){
        this.max = ma;
    }
    
    public int getID(){
        return this.id;
    }
    
    public String getName(){
        return this.name;
    }
    
    public double getPrice(){
        return this.price;
    }
    
    public int getStock(){
        return this.stock;
    }
    
    public int getMin(){
        return this.min;
    }
    
    public int getMax(){
        return this.max;
    }
    
    public void addAssociatedPart(Part part){
        if(part != null){
            this.associatedParts.add(part);
        }
    }
    
    public boolean deleteAssociatedPart(Part part){
        if(part != null){
            this.associatedParts.remove(part);
            return true;
        }
        return false;
    }
    
    public ObservableList<Part> getAllAssociatedParts(){
        return FXCollections.observableList(associatedParts);
    }






}
