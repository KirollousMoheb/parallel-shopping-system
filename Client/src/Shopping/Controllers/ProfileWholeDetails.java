package Shopping.Controllers;

import Shopping.Order;
import Shopping.Product;
import Shopping.Signleton;
import Shopping.SocketClient;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.util.ArrayList;

public class ProfileWholeDetails {

    private static ProfileWholeDetails klo = new ProfileWholeDetails();
    @FXML
    private AnchorPane History;

    @FXML
    private Button HistoryButton;

    @FXML
    private Button ProfileButton;

    @FXML
    private AnchorPane ProfileDetails;
    @FXML
    private ImageView backButton;
    @FXML


    private VBox orderesContainer;
    @FXML
    private AnchorPane Balance;

    @FXML
    private Button SignOutButton;



    @FXML
    private Button balanceButton;

    @FXML
    private Label currentbalancelabeldata;
    @FXML
    private Label currentbalancelabeldata1;

    @FXML
    private Label numberOfOrders;
    @FXML
    private TextField addbalanceTextfiled;

    @FXML
    private Button ConfirmBalanceButton;

    @FXML
    private Button addBalanceButton;

    @FXML
    private Label username;

    @FXML
    private Label username2;

    @FXML
    private Label name;

    private ArrayList<Order> array;

    @FXML
    void handleMouseEnter(MouseEvent event) {

        backButton.setScaleX(1.2);
        backButton.setScaleY(1.2);

    }

    @FXML
    void handleMouseExit(MouseEvent event) {
        backButton.setScaleX(1.0);
        backButton.setScaleY(1.0);


    }
    @FXML
    void BackButClicked(MouseEvent event)throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //Market Screen
        FXMLLoader MarketPaneLoader = new FXMLLoader(getClass().getResource("market.fxml"));
        Parent MarketPane = MarketPaneLoader.load();
        Scene MarketScene = new Scene(MarketPane, 1520, 720);
        primaryStage.setScene(MarketScene);
    }

    @FXML
    void BalanceButClicked(ActionEvent event) {
        ProfileDetails.setVisible(false);
        History.setVisible(false);
        Balance.setVisible(true);
        ProfileButton.setStyle(".saw-button");
        HistoryButton.setStyle(".saw-button");
        SignOutButton.setStyle(".saw-button");
        balanceButton.setStyle("-fx-background-color: linear-gradient(to right, #1e222b ,#2e3440); -fx-scale-x: 1; -fx-scale-y: 1.1;");


    }

    @FXML
    void HistoryButClicked(ActionEvent event) {
        ProfileDetails.setVisible(false);
        Balance.setVisible(false);
        History.setVisible(true);
        ProfileButton.setStyle(".saw-button");
        balanceButton.setStyle(".saw-button");
        SignOutButton.setStyle(".saw-button");
        HistoryButton.setStyle("-fx-background-color: linear-gradient(to right, #1e222b ,#2e3440); -fx-scale-x: 1; -fx-scale-y: 1.1;");
        generateOrdersList(array);


    }

    @FXML
    void ProfileButClicked(ActionEvent event) {
        ProfileDetails.setVisible(true);
        History.setVisible(false);
        Balance.setVisible(false);
        HistoryButton.setStyle(".saw-button");
        balanceButton.setStyle(".saw-button");
        SignOutButton.setStyle(".saw-button");
        ProfileButton.setStyle("-fx-background-color: linear-gradient(to right, #1e222b ,#2e3440); -fx-scale-x: 1; -fx-scale-y: 1.1;");

    }


    public static ProfileWholeDetails getInstance()
    {
        return klo;
    }


    void generateOrdersList(ArrayList<Order> arr){
        orderesContainer.getChildren().clear();
        int size = arr.size();
        ArrayList<OrderDataTable> order_array =new ArrayList<OrderDataTable>();

        for(int i = 0;i<size;i++){

           Node root = null;
        OrderDataTable controller =null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderDataTable.fxml"));
            root = loader.load();
            controller = loader.<OrderDataTable>getController();
        } catch (IOException e) {
            e.printStackTrace();
        }

        assert controller != null;
        controller.setData(arr.get(i).getId(),arr.get(i).getTotal_money(),arr.get(i).getProducts());
            orderesContainer.getChildren().add(root);
        }
    }

    @FXML
    void signoutButClicked(ActionEvent event) throws IOException {
        ProfileDetails.setVisible(false);
        History.setVisible(false);
        HistoryButton.setStyle(".saw-button");
        balanceButton.setStyle(".saw-button");
        ProfileButton.setStyle(".saw-button");
        Balance.setVisible(false);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //Market Screen
        FXMLLoader LoginPaneLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent LoginPane = LoginPaneLoader.load();
        Scene LoginScene = new Scene(LoginPane, 850, 500);
        primaryStage.setScene(LoginScene);

    }

    @FXML
    void addBalanceButtonclk(ActionEvent event) {
        addbalanceTextfiled.setDisable(false);
        addBalanceButton.setDisable(true);
        ConfirmBalanceButton.setDisable(false);
        addbalanceTextfiled.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue,
                                String newValue) {
                if (!newValue.matches("\\d*")) {
                    addbalanceTextfiled.setText(newValue.replaceAll("[^\\d*)]", ""));
                }
            }
        });

    }
    Signleton s=Signleton.getInstance();
    @FXML
    void confirmButClicked(ActionEvent event) {
        String current_money = currentbalancelabeldata.getText();
        int oldish=Integer.parseInt(current_money);
        String new_money = addbalanceTextfiled.getText();
        int newsh = Integer.parseInt(new_money);
        oldish +=newsh;


        int new_amount= Integer.parseInt(new_money);
        SocketClient socketClient = SocketClient.getInstance();
        JSONObject data2 = new JSONObject();
        data2.put("amount",new_amount);
        data2.put("user_name",s.getUsername());
        JSONObject new_balance_result = socketClient.socketSendReceiveJSON(data2,"addBalance");


        currentbalancelabeldata.setText(Integer. toString(oldish));
        currentbalancelabeldata1.setText(Integer. toString(oldish));
        addbalanceTextfiled.setText(" ");
        addbalanceTextfiled.setDisable(true);
        addBalanceButton.setDisable(false);
        ConfirmBalanceButton.setDisable(true);





    }

    String getBalance(){
        return currentbalancelabeldata.getText();
    }


    void setGlobalData(String username,String name,String balance,ArrayList<Order> array){

        ArrayList<Product> products=new ArrayList<>();

        for (int i=0;i<array.size();i++){
            products=array.get(i).getProducts();

            for(int j=0;j<products.size();j++){
                System.out.println(products.get(j).getName()+" "+products.get(j).getQuantity());
            }
        }


        this.username.setText("@"+username);
        this.username2.setText("@"+username);
        this.name.setText(name);
        this.currentbalancelabeldata.setText(balance);
        this.currentbalancelabeldata1.setText(balance);
        this.array =array;
        int size = array.size();
        this.numberOfOrders.setText(Integer.toString(size));
        if(!ProfileDetails.isDisabled()){
            ProfileButton.setStyle("-fx-background-color: linear-gradient(to right, #1e222b ,#2e3440); -fx-scale-x: 1; -fx-scale-y: 1.1;");
        }
        if(!addBalanceButton.isDisabled()){
            ConfirmBalanceButton.setDisable(true);
        }

    }
}
