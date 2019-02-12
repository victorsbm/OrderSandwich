/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ordersandwich;

import javafx.application.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.*;
import javax.swing.JOptionPane;
import javafx.stage.*;
import javafx.geometry.*;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;


/**
 *
 * @author Owner
 */
public class OrderSandwich extends Application {
    
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        
        Text textHeading = new Text("Create your own sandwich");
        textHeading.setFont(new Font(20));
        HBox paneTop = new HBox(textHeading);
        paneTop.setPadding(new Insets(20, 10, 20, 10));
        
        
        Label lblName = new Label("Name:");
        lblName.setPrefWidth(100);
        nameText = new TextField();
        nameText.setPrefColumnCount(20);
        nameText.setPromptText("Enter your name here: ");
        nameText.setMaxWidth(Double.MAX_VALUE);
        HBox paneName = new HBox(lblName, nameText);
        
        
        Label lblPhone = new Label("Phone: ");
        lblPhone.setPrefWidth(100);
        phoneText = new TextField();
        phoneText.setPrefColumnCount(20);
        phoneText.setPromptText("Enter your phone number here: ");
        HBox panePhone = new HBox(lblPhone, phoneText);

        Label lblAddress = new Label("Address: ");
        lblAddress.setPrefWidth(100);
        addressText = new TextField();
        addressText.setPrefColumnCount(20);
        addressText.setPromptText("Enter your address here: ");
        HBox paneAddress = new HBox(lblAddress, addressText);
        
        
        VBox paneCustomer = new VBox(10, paneName, panePhone, paneAddress);
        
        Label lblBread = new Label("Bread");
        radioItalian = new RadioButton("Italian");
        radioHoneyOat = new RadioButton("Honey Oat");
        radioFlatbread = new RadioButton("Flat Bread");    
        radioFlatbread.setSelected(true);
        ToggleGroup groupBread = new ToggleGroup();
        radioItalian.setToggleGroup(groupBread);
        radioHoneyOat.setToggleGroup(groupBread);
        radioFlatbread.setToggleGroup(groupBread);            
        
        VBox paneBread = new VBox(lblBread, radioItalian, radioHoneyOat, radioFlatbread);
        paneBread.setSpacing(10);
        
        Label lblSize = new Label("Size:");
        radio6Inch = new RadioButton("6-inches");
        radioFootlong = new RadioButton("Footlong");
        radioFootlong.setSelected(true);
        ToggleGroup groupSize = new ToggleGroup();
        radio6Inch.setToggleGroup(groupSize);
        radioFootlong.setToggleGroup(groupSize);
           
        VBox paneSize = new VBox(lblSize, radio6Inch, radioFootlong);
        paneSize.setSpacing(10);
        
        Label lblToppings = new Label("Toppings");
        chkLettuce = new CheckBox("Lettuce");
        chkTomatoes = new CheckBox("Tomatoes");
        chkOnions = new CheckBox("Onions");
        chkCucumbers = new CheckBox("Cucumbers");
        chkPickles = new CheckBox("Pickles");
        chkCapsicums = new CheckBox("Capsicums");
        
        FlowPane paneToppings = new FlowPane(Orientation.VERTICAL, chkLettuce,chkTomatoes, chkOnions, chkCucumbers, chkPickles, chkCapsicums);
        paneToppings.setPadding(new Insets(10, 0, 10, 0));
        paneToppings.setHgap(20);
        paneToppings.setVgap(10);
        paneToppings.setPrefWrapLength(100);
        
        VBox paneTopping = new VBox(lblToppings, paneToppings);
        
        HBox paneOrder = new HBox(50, paneBread, paneSize, paneTopping);
        
        VBox paneCenter = new VBox(20, paneCustomer, paneOrder);
        paneCenter.setPadding(new Insets(0, 10, 0, 10));
        
        Button btnOK = new Button("OK");
        btnOK.setPrefWidth(80);
        btnOK.setOnAction(e -> btnOK_Click());
        
        Button btnCancel = new Button("Cancel");
        btnCancel.setPrefWidth(80);
        btnCancel.setOnAction(e -> btnCancel_Click());        
        
        Region spacer = new Region();
        
        HBox paneBottom = new HBox(10, spacer, btnOK, btnCancel);
        paneBottom.setHgrow(spacer, Priority.ALWAYS);
        paneBottom.setPadding(new Insets(20, 10, 20, 10));

        BorderPane paneMain = new BorderPane();
        paneMain.setTop(paneTop);
        paneMain.setCenter(paneCenter);
        paneMain.setBottom(paneBottom);
        
        Scene scene = new Scene(paneMain);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Sandwich Order");
        primaryStage.show();
    }
    public void btnOK_Click(){
        String msg = "Customer Information:\n\n";
        String secondmsg = "";
        msg += "\t" + nameText.getText() + "\n";
        msg += "\t" + phoneText.getText() + "\n";
        msg += "\t" + addressText.getText() + "\n\n";
        secondmsg += "Dear customer: \n";
        secondmsg += "You have ordered a ";
        
        if (radio6Inch.isSelected()){
            secondmsg += "6-inch ";
        } 
        
        if (radioFootlong.isSelected()){
            secondmsg += "footlong ";
        }   
        
        if (radioItalian.isSelected()){
            secondmsg += "Italian with ";
        };
        if (radioHoneyOat.isSelected()){
            secondmsg += "Honey Oat with ";
        }; 
        if (radioFlatbread.isSelected()){
            secondmsg += "Flat bread with ";
        };
        
        String toppings = "";
        toppings = buildToppings(chkLettuce, toppings);
        toppings = buildToppings(chkTomatoes, toppings);
        toppings = buildToppings(chkOnions, toppings);
        toppings = buildToppings(chkCucumbers, toppings);
        toppings = buildToppings(chkPickles, toppings);
        toppings = buildToppings(chkCapsicums, toppings);
        
        if (toppings == ""){
            secondmsg += "no toppings";
        } else {
            secondmsg += "the following topping(s):\n" + toppings;
        }

        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Order Deteils");
        alert.setHeaderText(msg);
        alert.setContentText(secondmsg);
        alert.showAndWait();
        
        stage.close();
        
        
    }
    
    
    
    public String buildToppings(CheckBox chk, String msg){
        if (chk.isSelected()){
            if(!msg.equals("")){
                msg += ", ";
            }
            msg += chk.getText();
        }
        
        return msg; 
    }
    
    
    public void btnCancel_Click(){
        stage.close();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    Stage stage;
    
    TextField nameText;
    TextField phoneText;
    TextField addressText;
    
    RadioButton radioItalian;
    RadioButton radioHoneyOat;
    RadioButton radioFlatbread;
    
    RadioButton radio6Inch;
    RadioButton radioFootlong;
    
    CheckBox chkLettuce;
    CheckBox chkTomatoes;
    CheckBox chkOnions;
    CheckBox chkCucumbers;
    CheckBox chkPickles;
    CheckBox chkCapsicums;
    

}
