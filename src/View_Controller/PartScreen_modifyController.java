package View_Controller;

import Model.InHouse;
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
public class PartScreen_modifyController implements Initializable {

    @FXML private ToggleGroup type;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private Label changeField;
    @FXML private TextField companyOrIdField;
    @FXML private RadioButton inhouse_button;
    @FXML private RadioButton outsourced_button;
    
    private Inventory inv;
    private Part part;
    private InHouse inhousePart;
    private Outsourced outsourcedPart;
    private int index;
    
            
    public PartScreen_modifyController(Inventory inv, Part part, int index){
        this.inv = inv;
        this.part = part;
        this.index = index;
    }
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        idField.setText(String.valueOf(part.getID()));
        idField.setDisable(true);
        nameField.setText(part.getName());
        priceField.setText(String.valueOf(part.getPrice()));
        minField.setText(String.valueOf(part.getMin()));
        maxField.setText(String.valueOf(part.getMax()));
        invField.setText(String.valueOf(part.getStock()));
        boolean isInhouse = this.part instanceof InHouse;
        boolean isOutsourced = this.part instanceof Outsourced;
        if(isInhouse == true){
            inhousePart = (InHouse) part;
            inhouse_button.setSelected(true);
            changeField.setText("Machine ID");
            companyOrIdField.setText(String.valueOf(inhousePart.getMachineID()));
        }
        if(isOutsourced == true){
            outsourcedPart = (Outsourced) part;
            outsourced_button.setSelected(true);
            changeField.setText("Company Name");
            companyOrIdField.setText((outsourcedPart.getCompanyName()));
        }
        
        
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
        if(companyOrIdField.getText().isEmpty() == true){
            companyOrIdField.setText("0");
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
                inv.updatePart(index,(new InHouse((Integer.valueOf(idField.getText())), (nameField.getText()), (Double.valueOf(priceField.getText())), 
                            (Integer.valueOf(invField.getText())), (Integer.valueOf(minField.getText())), (Integer.valueOf(maxField.getText())),
                            (Integer.valueOf(companyOrIdField.getText())))));
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
                inv.updatePart(index,(new Outsourced((Integer.valueOf(idField.getText())), (nameField.getText()), (Double.valueOf(priceField.getText())), 
                            (Integer.valueOf(invField.getText())), (Integer.valueOf(minField.getText())), (Integer.valueOf(maxField.getText())),
                            (String.valueOf(companyOrIdField.getText())))));
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
    private void cancelButton(MouseEvent event) throws IOException {
        cancelPartConfirmation("","Cancel part modification?", event);
        /*Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/Screen.fxml"));
        
        View_Controller.ScreenController controller = new View_Controller.ScreenController(inv);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();*/
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
    private void changeFieldInhouse(MouseEvent event) {
        changeField.setText("Machine ID");
    }

    @FXML
    private void changeFieldOutsourced(MouseEvent event) {
        changeField.setText("Company Name");
    }
    
}
