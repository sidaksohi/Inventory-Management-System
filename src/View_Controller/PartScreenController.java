package View_Controller;

import javafx.stage.*;
import Model.*;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.control.*;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.scene.*;
import javafx.geometry.*;

/**
 * FXML Controller class
 *
 * @author Sidak
 */
public class PartScreenController implements Initializable {

    @FXML private ToggleGroup type;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField companyIdField;
    @FXML private Label changeField;
    
    private Inventory inv;
    private int uniqueID;
    
    
    /**
     * Initializes the controller class.
     */
    
    
    
    public PartScreenController(Inventory inv){
        this.inv = inv;
    } 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        uniqueID = (inv.getAllParts()).size() + 1;
        idField.setText(String.valueOf(uniqueID));
        idField.setDisable(true);
        
       
        
    }
    
    private void alert(String title, String message){
        Stage window = new Stage();
        
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(300);
        
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("Close");
        closeButton.setOnAction(e -> window.close());
        
        VBox layout = new VBox(10);
        layout.getChildren().addAll(label, closeButton);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
    
    
    @FXML
    private void savePart(MouseEvent event) throws IOException {
        if(companyIdField.getText().isEmpty() == true){
            companyIdField.setText("0");
        }
        if(nameField.getText().isEmpty() == true || priceField.getText().isEmpty() == true || invField.getText().isEmpty()){
            alert("Error", "Name, Price, or Inventory Count missing");
            return;
        }
        if(Integer.valueOf(minField.getText()) > Integer.valueOf(maxField.getText())){
            alert("Error", "Min must be lower than Max");
            return;
        }
        if(Integer.valueOf(minField.getText()) < 0){
            alert("Error", "Min cannot be negative");
            return;
        }
        if(Integer.valueOf(invField.getText()) < Integer.valueOf(minField.getText()) ||  Integer.valueOf(invField.getText()) > Integer.valueOf(maxField.getText())){
            alert("Error", "Invalid inventory count");
            return;
        }
            if (changeField.getText() == "Machine ID"){
                inv.addPart(new InHouse((Integer.valueOf(idField.getText())), (nameField.getText()), (Double.valueOf(priceField.getText())), 
                            (Integer.valueOf(invField.getText())), (Integer.valueOf(minField.getText())), (Integer.valueOf(maxField.getText())),
                            (Integer.valueOf(companyIdField.getText()))));
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Screen.fxml"));

                View_Controller.ScreenController controller = new View_Controller.ScreenController(inv);
                loader.setController(controller);
                Parent root = loader.load();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();

            }else if (changeField.getText() == "Company Name"){
                inv.addPart(new Outsourced((Integer.valueOf(idField.getText())), (nameField.getText()), (Double.valueOf(priceField.getText())), 
                            (Integer.valueOf(invField.getText())), (Integer.valueOf(minField.getText())), (Integer.valueOf(maxField.getText())),
                            (String.valueOf(companyIdField.getText()))));
                Stage stage = new Stage();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Screen.fxml"));        
                View_Controller.ScreenController controller = new View_Controller.ScreenController(inv);
                loader.setController(controller);        
                Parent root = loader.load();        
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.setResizable(false);
                stage.show();
                ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
            }else alert("Error", "Must select part type"); 
    }

    @FXML
    private void cancelPart(MouseEvent event) throws IOException {
        cancelPartConfirmation("","Cancel adding a part?", event);
    }
    
    private void cancelPartConfirmation(String title, String message, MouseEvent event) throws IOException{
        
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        
        closeButton.setOnAction(e -> deletePr(window, event));
        
        cancelButton.setOnAction(e -> window.close());
        
        
        VBox layout = new VBox(10);
        HBox buttons = new HBox(10);
        buttons.getChildren().addAll(closeButton, cancelButton);
        buttons.setAlignment(Pos.CENTER);
        layout.getChildren().addAll(label, buttons);
        layout.setAlignment(Pos.CENTER);
        
        Scene scene = new Scene(layout);
        window.setScene(scene);
        window.show();
    }
    
    private void deletePr(Stage window, MouseEvent event){
        window.close();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        try{
            openMain();
        }catch(Exception e){
            
        }
    }
    
    private void openMain() throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Screen.fxml"));
        
        View_Controller.ScreenController controller = new View_Controller.ScreenController(inv);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }
    

    @FXML
    private void machineID(MouseEvent event) {
        changeField.setText("Machine ID");
    }

    @FXML
    private void companyName(MouseEvent event) {
        changeField.setText("Company Name");
    }
    
}
