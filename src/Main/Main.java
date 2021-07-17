package Main;

import Model.*;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Sidak
 */
public class Main extends Application {
    
    public static void main(String[] args) {
        launch(args);
    }
    public boolean on = true;
    
    @Override
    public void start(Stage stage) throws Exception {
        Inventory inv = new Inventory();
        addTestData(inv);
       
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Screen.fxml"));
        
        View_Controller.ScreenController controller = new View_Controller.ScreenController(inv);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    
    public void addTestData(Inventory inv){
        //InHouse Parts
        Part a1 = new InHouse(1, "Part A1", 2.99, 10, 5, 100, 101);
        Part a2 = new InHouse(3, "Part A2", 4.99, 11, 5, 100, 103);
        Part a3 = new InHouse(2, "Part B", 3.99, 9, 5, 100, 102);
        
        inv.addPart(a1);
        inv.addPart(a2);
        inv.addPart(a3);
        inv.addPart(new InHouse(4, "Part A3", 5.99, 15, 5, 100, 104));
        inv.addPart(new InHouse(5, "Part A4", 6.99, 5, 5, 100, 105));
        
        //OutSourced Parts
        Part o1 = new Outsourced(6, "Part 01", 2.89, 10, 5, 100, "SIDAK Co.");
        Part o2 = new Outsourced(7, "Part 02", 3.89, 25, 25, 100, "Soso Co.");
        Part o3 = new Outsourced(8, "Part 03", 4.20, 25, 30, 100, "Issa Co.");
        inv.addPart(o1);
        inv.addPart(o2);
        inv.addPart(o3);
        //Products
        Product prod1 = new Product(99, "Product 4", 9.99, 20, 5, 100);
        Product prod2 = new Product(101, "Product 2", 19.99, 30, 15, 100);
        inv.addProduct(prod1);
        inv.addProduct(prod2);
        prod1.addAssociatedPart(a1);
        prod2.addAssociatedPart(a3);
        
    }
    
}
