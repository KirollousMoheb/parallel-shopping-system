package Shopping.Controllers;

import Shopping.Item;
import Shopping.Main;
import Shopping.Signleton;
import Shopping.SocketClient;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

public class CartController implements Initializable {


    ArrayList<Item> cartItems = new ArrayList<Item>();
    ArrayList<Integer>realPrice=new ArrayList<Integer>();
    Signleton s=Signleton.getInstance();

    private ArrayList<Integer> ItemsCount=new ArrayList<Integer>();

    public ArrayList<Integer> getItemsCount() {
        return ItemsCount;
    }

    public void setItemsCount(ArrayList<Integer> itemsCount) {
        ItemsCount = itemsCount;
    }

    public ArrayList<Integer> getItemsId() {
        return ItemsId;
    }
    public void setItemsId(ArrayList<Integer> itemsId) {
        ItemsId = itemsId;
    }

    private ArrayList<Integer> ItemsId=new ArrayList<Integer>();


    public ArrayList<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(Item cartItem) {
        this.cartItems.add(cartItem);
    }


    @FXML
    private Button one_more_button;
    @FXML
    private Button backButton;
    @FXML
    private Button check_out;

    @FXML
    private TableColumn<Item, String> itemCategory;


    @FXML
    private TableColumn<Item, Integer> itemCount;

    @FXML
    private TableColumn<Item, String> itemName;

    @FXML
    private TableColumn<Item, Double> itemPrice;

    @FXML
    private Label lab2;

    @FXML
    private Label lab3;
    @FXML
    private Label notenough;
    @FXML
    private Button removeButton;

    @FXML
    private TableView<Item> table;

    @FXML
    private Label title;
    @FXML
    private Label totalPrice;


    @FXML
    void removeItem(ActionEvent event) {
        int selectedID = table.getSelectionModel().getSelectedIndex();
        table.getItems().get(selectedID).decreamentCount();

        ObservableList<Item> items=table.getItems();

        for(int i=0;i<cartItems.size();i++){

            int localPrice=0;
            localPrice=cartItems.get(i).getItemCartCount()*realPrice.get(i);
            cartItems.get(i).setPrice(localPrice);
            items.set(i,cartItems.get(i));
        }
        QtySelected.setText(String.valueOf(table.getItems().get(selectedID).getItemCartCount()));

        if(table.getItems().get(selectedID).getItemCartCount()==0){
            table.getItems().remove(selectedID);
            cartItems.remove(selectedID);
            realPrice.remove(selectedID);
            QtySelected.setText( "  ");
            s.getCartItems().remove(selectedID);
        }

        calculateTotalPrice(cartItems);

    }
    @FXML
    void clickOneMore(ActionEvent event) {
        int selectedID = table.getSelectionModel().getSelectedIndex();
        table.getItems().get(selectedID).increamentCount();

        ObservableList<Item> items=table.getItems();

        for(int i=0;i<cartItems.size();i++){

            int localPrice=0;
            localPrice=cartItems.get(i).getItemCartCount()*realPrice.get(i);
            cartItems.get(i).setPrice(localPrice);
            items.set(i,cartItems.get(i));
        }
        QtySelected.setText(String.valueOf(table.getItems().get(selectedID).getItemCartCount()));

        calculateTotalPrice(cartItems);

    }
    @FXML
    private Label QtySelected;

    @FXML
    void getQty(MouseEvent event){
        int selectedID = table.getSelectionModel().getSelectedIndex();

        QtySelected.setText(String.valueOf(table.getItems().get(selectedID).getItemCartCount()));


    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        ArrayList<Item> new_cart= s.getCartItems();

        for (int i=0;i<new_cart.size();i++){
            Item item=new Item();
            item.setCategory(new_cart.get(i).getCategory());
            item.setName(new_cart.get(i).getName());
            item.setPrice(new_cart.get(i).getPrice());;
            item.setItemCartCount(new_cart.get(i).getItemCartCount());
            item.setId(new_cart.get(i).getId());
            cartItems.add(item);
        }
        itemName.setCellValueFactory(new PropertyValueFactory<Item,String>("name"));
        itemPrice.setCellValueFactory(new PropertyValueFactory<Item,Double>("price"));
        itemCategory.setCellValueFactory(new PropertyValueFactory<Item,String>("Category"));
        itemCount.setCellValueFactory(new PropertyValueFactory<Item,Integer>("itemCartCount"));

        saveRealPrice();

        ObservableList<Item> items=table.getItems();

        for(int i=0;i<cartItems.size();i++){
            int localPrice=0;
            localPrice=cartItems.get(i).getItemCartCount()*cartItems.get(i).getPrice();
            cartItems.get(i).setPrice(localPrice);
            items.add(cartItems.get(i));
        }

        table.setItems(items);
        calculateTotalPrice(cartItems);
    }

    void calculateTotalPrice(ArrayList<Item> localItems){
        int totalAmountOfMoney =0;
        for(int i=0;i<localItems.size();i++){
            totalAmountOfMoney+=localItems.get(i).getPrice();
        }
        totalPrice.setText(String.valueOf(totalAmountOfMoney));
    }

    @FXML
    void clickBack(ActionEvent event) throws IOException {
        Collections.copy(s.getCartItems(),cartItems);
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //Market Screen
        FXMLLoader MarketPaneLoader = new FXMLLoader(getClass().getResource("market.fxml"));
        Parent MarketPane = MarketPaneLoader.load();
        Scene MarketScene = new Scene(MarketPane, 1520, 720);
        primaryStage.setScene(MarketScene);
    }

    @FXML
    void clickCheckOut(ActionEvent event) throws IOException {


        SocketClient socketClient = SocketClient.getInstance();
        JSONObject json = new JSONObject();
        JSONObject d = new JSONObject();
        d.put("user_name" , s.getUsername());
        JSONObject balance_result = socketClient.socketSendReceiveJSON(d,"getBalance");
        int balance = ((Long)balance_result.get("balance")).intValue();
        int total_price= Integer.parseInt(totalPrice.getText());



        if(balance >= total_price && total_price!=0)
        {
            for(int i=0;i<cartItems.size();i++){
                ItemsCount.add(cartItems.get(i).getItemCartCount());
                ItemsId.add(cartItems.get(i).getId());
            }
            for (int i=0;i<ItemsCount.size();i++){
                System.out.println(ItemsCount.get(i));
                System.out.println(ItemsId.get(i));

            }
            QtySelected.setText( "  ");
            JSONObject data = new JSONObject();

            String user_name = s.getUsername();
            data.put("products_ids",ItemsId);
            data.put("quantities",ItemsCount);
            data.put("total_price",total_price);
            data.put("user_name",user_name);
            System.out.println("order added");
            JSONObject result = socketClient.socketSendReceiveJSON(data,"addOrder");
            s.getCartItems().clear();
            while(!(cartItems.isEmpty())){
                cartItems.remove(0);
            }
            table.getItems().clear();
            totalPrice.setText("0");
            //Market Screen
            Collections.copy(s.getCartItems(),cartItems);
            Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
            FXMLLoader MarketPaneLoader = new FXMLLoader(getClass().getResource("market.fxml"));
            Parent MarketPane = MarketPaneLoader.load();
            Scene MarketScene = new Scene(MarketPane, 1520, 720);
            primaryStage.setScene(MarketScene);
        }
        else
        {

            notenough.setText("Not Enough Balance");
        }


    }

    void saveRealPrice(){

        for(int i=0;i<cartItems.size();i++){
            realPrice.add(cartItems.get(i).getPrice());
        }
    }
}
