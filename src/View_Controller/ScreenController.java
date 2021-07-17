package View_Controller;

import javafx.stage.Stage;
import javafx.application.Platform;
import Model.Inventory;
import Model.Part;
import Model.Product;
import java.io.IOException;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.ArrayList;
import javafx.application.Application;
import javafx.collections.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Modality;

/**
 * FXML Controller class
 *
 * @author Sidak
 */
public class ScreenController implements Initializable {

    @FXML private AnchorPane screen;

    
    @FXML private TableView<Part> partsTable;
    @FXML private TableColumn<Part, Integer> partIDCol;
    @FXML private TableColumn<Part, String> nameCol;
    @FXML private TableColumn<Part, Integer> invCountCol;
    @FXML private TableColumn<Part, Double> priceCol;

    
    @FXML private TextField searchPartsField;
    @FXML private TextField searchProductsField;
    
    @FXML private TableView<Product> productsTable;
    @FXML private TableColumn<Product, Integer> productIDcol;
    @FXML private TableColumn<Product, String> prodNameCol;
    @FXML private TableColumn<Product, Integer> prodInvCol;
    @FXML private TableColumn<Product, Double> prodPriceCol;
    
    
      
    private Inventory inv;
   
    //Initializes the controller class.
 
    public ScreenController(Inventory x){
        this.inv = x;
    } 
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generatePartsTable();
        partIDCol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        invCountCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        generateProductsTable();
        productIDcol.setCellValueFactory(new PropertyValueFactory<>("ID"));
        prodNameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        prodInvCol.setCellValueFactory(new PropertyValueFactory<>("stock"));
        prodPriceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
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
    
    private void generatePartsTable(){
        partsTable.setItems(inv.getAllParts());
        partsTable.refresh();
    }
    
    private void generateProductsTable(){
        productsTable.setItems(inv.getAllProducts());
        partsTable.refresh();
    }
    
    
    
    
    
    
    
    
    
    @FXML
    private void searchPart(MouseEvent event) {
        if (searchPartsField.getText().isEmpty() == false){
            partsTable.setItems(inv.lookupPart(searchPartsField.getText()));
            partsTable.refresh();
        }else{
            partsTable.setItems(inv.getAllParts());
            partsTable.refresh();
            }
        
    }

    @FXML
    private void addPart(MouseEvent event) throws IOException {
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/PartScreen.fxml"));
        
        View_Controller.PartScreenController controller = new View_Controller.PartScreenController(inv);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void modifyPart(MouseEvent event) throws IOException {
        if(partsTable.getSelectionModel().getSelectedItem() == null){
            alert("Error","A part must be selected to modify");
            return;
        }
        Part part = partsTable.getSelectionModel().getSelectedItem();
        int index = partsTable.getSelectionModel().getSelectedIndex();
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/PartScreen_modify.fxml"));
        
        View_Controller.PartScreen_modifyController controller = new View_Controller.PartScreen_modifyController(inv,part,index);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
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
        closeButton.setOnAction(e -> deleteP(window));
        
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
    
    private void deleteP(Stage stage){
        if(partsTable.getItems().size() != inv.getAllParts().size()){
            alert("Error","Cannot delete part while in search results screen");
            partsTable.setItems(inv.getAllParts());
            partsTable.refresh();
            return;
        }
        
        int selectedIndex = partsTable.getSelectionModel().getSelectedIndex();
        inv.deletePart(selectedIndex);
        
        partsTable.setItems(inv.getAllParts());
        partsTable.refresh();
        
        stage.close();
    }

    @FXML
    private void searchProduct(MouseEvent event) {
        if (searchProductsField.getText() != null){
            if (inv.lookupPart(searchProductsField.getText()) == null){
                productsTable.setItems(inv.getAllProducts());
                productsTable.refresh();
                } else {
                    productsTable.setItems(inv.lookupProduct(searchProductsField.getText()));
                    productsTable.refresh();
                    }
            
        }else {
            productsTable.setItems(inv.getAllProducts());
            productsTable.refresh();
            }
    }

    @FXML
    private void addProduct(MouseEvent event) throws IOException{
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ProductScreen.fxml"));
        
        View_Controller.ProductScreenController controller = new View_Controller.ProductScreenController(inv);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void modifyProduct(MouseEvent event) throws IOException{
        if(productsTable.getSelectionModel().getSelectedItem() == null){
            alert("Error","Must select a product to modify");
            return;
        }
        
        Product product = productsTable.getSelectionModel().getSelectedItem();
        int index = productsTable.getSelectionModel().getSelectedIndex();
        
        Stage stage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/View_Controller/ProductScreen_modify.fxml"));
        
        View_Controller.ProductScreen_modifyController controller = new View_Controller.ProductScreen_modifyController(inv, product, index);
        loader.setController(controller);
        
        Parent root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
        
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    private void deleteProduct(MouseEvent event) {
        deleteProductConfirmation("","Delete product?");
        
    }
    
    private void deleteProductConfirmation(String title, String message){
        
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
        if(productsTable.getItems().size() != inv.getAllProducts().size()){
            alert("Error","Cannot delete product while in search results screen");
            productsTable.setItems(inv.getAllProducts());
            productsTable.refresh();
            return;
        }
        int selectedIndex = productsTable.getSelectionModel().getSelectedIndex();
        inv.deleteProduct(selectedIndex); 
        
        productsTable.setItems(inv.getAllProducts());
        productsTable.refresh();
        
        stage.close();
    }
    
    
    @FXML private void exitScreen(MouseEvent event){
    
        
        exitConfirmation("", "Are you sure you want to exit?");
    }
    
    private void exitConfirmation(String title, String message){
        
        Stage window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(350);
        
        Label label = new Label();
        label.setText(message);
        Button closeButton = new Button("OK");
        Button cancelButton = new Button("Cancel");
        closeButton.setOnAction(e -> Platform.exit());
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
    
    
   
}
