package Model;

/**
 *
 * @author Sidak
 */
public class InHouse extends Part {
    private int machineID;
    
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineID){
        setID(id);
        setName(name);
        setPrice(price);
        setStock(stock);
        setMin(min);
        setMax(max);
        setMachineID(machineID);        
    }
    
    public void setMachineID(int mID){
        this.machineID = mID;
    }
    
    public int getMachineID(){
        return this.machineID;
    }
    
    
    
}
