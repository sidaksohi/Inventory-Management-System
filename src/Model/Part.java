package Model;

/**
 *
 * @author Sidak
 */
public abstract class Part {
    protected int id;
    protected String name;
    protected double price;
    protected int stock;
    protected int min;
    protected int max;

    public void Part(int id, String name, double price, int stock, int min, int max){
        
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





}

