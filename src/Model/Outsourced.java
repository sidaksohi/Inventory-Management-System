package Model;

/**
 *
 * @author Sidak
 */
public class Outsourced extends Part {
    private String companyName;
    
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName){
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setCompanyName(companyName);
    }
    
    public void setCompanyName(String coName){
        this.companyName = coName;
    }
    
    public String getCompanyName(){
        return this.companyName;
    }
    
    
    
}
