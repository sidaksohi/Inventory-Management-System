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
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * FXML Controller class
 *
 * @author Sidak
 */
public class ProductScreen_modifyController implements Initializable {

    @FXML private RadioButton inhouseButton;
    @FXML private ToggleGroup type;
    @FXML private RadioButton outsourcedButton;
    @FXML private TextField idField;
    @FXML private TextField nameField;
    @FXML private TextField invField;
    @FXML private TextField priceField;
    @FXML private TextField minField;
    @FXML private TextField maxField;
    @FXML private TextField searchField;   
    @FXML private TextField companyIdField;
    
    @FXML private TableView<Part> a_partsTable;
    @FXML private TableColumn<Part, String> a_nameCol;
    @FXML private TableColumn<Part, Double> a_priceCol;
    @FXML private TableColumn<Part, Integer> a_invCountCol;
    
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, String> nameCol;
    @FXML private TableColumn<Part, Double> priceCol;
    @FXML private TableColumn<Part, Integer> invCountCol;

    
    private Inventory inv;
    private Product product;
    private int index;
    private ArrayList<Part> a_parts = new ArrayList<Part>();
    
    /**
     * Initializes the controller class.
     */
    public ProductScreen_modifyController(Inventory inv, Product product, int index){
        this.inv = inv;
        this.product = product;
        this.index = index;
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        generatePartsTable();
        generatea_PartsTable();
        
        idField.setText(String.valueOf(product.getID()));
        nameField.setText(product.getName());
        priceField.setText(String.valueOf(product.getPrice()));
        invField.setText(String.valueOf(product.getStock()));
        minField.setText(String.valueOf(product.getMin()));
        maxField.setText(String.valueOf(product.getMax()));
        
        idField.setDisable(true);
        companyIdField.setDisable(true);
        inhouseButton.setDisable(true);
        outsourcedButton.setDisable(true);
    }
    
    
    
    public void generatePartsTable(){
        partsTable.setItems(inv.getAllParts());
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        invCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
    }
    
    public void generatea_PartsTable(){
        a_partsTable.setItems(product.getAllAssociatedParts());
        a_nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        a_priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        a_invCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));       
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
    private void saveProduct(MouseEvent event) throws IOException{
        double totalPrice = 0;
        
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
        if(Double.valueOf(priceField.getText()) < totalPrice){
            alert("Error", "Price of product cannot be less than price of associated parts");
            return;
        }
        if(a_partsTable.getItems().size() <= 0){
            alert("Error", "All products must have at least 1 associated part");
            return;
        }
        
        for(int i = 0; i < a_partsTable.getItems().size(); i++){
            a_parts.add(a_partsTable.getItems().get(i));
            totalPrice += a_priceCol.getCellData(i);
        }       
        
        inv.updateProduct( index, (new Product (Integer.valueOf(idField.getText()), nameField.getText(), Double.valueOf(priceField.getText()), Integer.valueOf(invField.getText()),
                                    Integer.valueOf(minField.getText()), Integer.valueOf(maxField.getText()), a_parts)));
        totalPrice = 0;
                    
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
                    
    }

    @FXML
    private void cancelProduct(MouseEvent event) throws IOException{
        cancelProductConfirmation("","Cancel product modification?", event);
    }
    
    private void cancelProductConfirmation(String title, String message, MouseEvent event) throws IOException{
        
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
    private void searchParts(MouseEvent event) {
        if (searchField.getText().isEmpty() == false){
            partsTable.setItems(inv.lookupPart(searchField.getText()));
            partsTable.refresh();
        }else{
            partsTable.setItems(inv.getAllParts());
            partsTable.refresh();
            }
    }

    @FXML
    private void addPart(MouseEvent event) {
        if(partsTable.getSelectionModel().getSelectedItem() == null){
            alert("Error","A part must be selected to add");
            return;
        }
        Part part = partsTable.getSelectionModel().getSelectedItem();
        a_partsTable.getItems().add(part);
        a_partsTable.refresh();
    }

    @FXML
    private void deletePart(MouseEvent event) {
        deletePartConfirmation("","Delete part?");
    }
    
    private void deletePartConfirmation(String title, String message){
        
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        closeButton.setOnAction(e -> deletePr(window));
        
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
    
    private void deletePr(Stage stage){
        if(a_partsTable.getSelectionModel().getSelectedItem() == null){
            alert("Error","A part must be selected to delete");
            return;
        }
        int index = a_partsTable.getSelectionModel().getSelectedIndex();
        a_partsTable.getItems().remove(index);
        
        stage.close();
    }
}
