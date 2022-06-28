package Shopping.Controllers;

import Shopping.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MarketController implements Initializable {
    Signleton s=Signleton.getInstance();




    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    private Scene ProfileScene;
    private ArrayList<Item> addedItems= new ArrayList<Item>();

    public ArrayList<Item> getAddedItems() {
        return addedItems;
    }

    public void setAddedItems(ArrayList<Item> addedItems) {
        this.addedItems = addedItems;
    }

    public void setProfileScene(Scene scene) {
        ProfileScene = scene;
    }








    public void goToProfile(ActionEvent actionEvent) throws IOException {










            Signleton s=Signleton.getInstance();

            SocketClient socketClient = SocketClient.getInstance();
            JSONObject json = new JSONObject();
            json.put("username",s.getUsername());

            String dist = "userProfile";
            JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);



            Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
            ProfileWholeDetails profileWholeDetails = ProfileWholeDetails.getInstance();

            Parent root = null;
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("ProfileWholeDetails.fxml"));
                root = loader.load();
                profileWholeDetails = loader.<ProfileWholeDetails>getController();
            } catch (IOException e) {
                e.printStackTrace();
            }

            assert profileWholeDetails != null;

        ArrayList<JSONObject> ordersJsonArray = (ArrayList<JSONObject>) serverResponse.get("Orders");
        ArrayList<Order> orders=new ArrayList<>();


        for (int i=0;i<ordersJsonArray.size();i++){
            String order_id=(String)ordersJsonArray.get(i).get("order-id");
            String total_money=(String)ordersJsonArray.get(i).get("order-total-money");
            String number_of_products= (String) ordersJsonArray.get(i).get("order-num-of-products");

            ArrayList<JSONObject> orderProductsArray = (ArrayList<JSONObject>) ordersJsonArray.get(i).get("order-products");
            ArrayList<Product> products=new ArrayList<>();

            for (int j=0;j<orderProductsArray.size();j++){
                String name= (String) orderProductsArray.get(j).get("product-name");
                String quantity=(String) orderProductsArray.get(j).get("product-qty");
                System.out.println(name +" "+ quantity);
                Product product=new Product(name,quantity);
                products.add(product);


            }

            Order order=new Order(order_id,total_money,number_of_products,products);
            orders.add(order);




        }




            String name = (String) serverResponse.get("Name");
            String balance =(String) serverResponse.get("Balance");



            profileWholeDetails.setGlobalData(s.getUsername(),name,balance, orders);
            primaryStage.setScene(new Scene(root,1520, 800));





    }
    public void goToCart(MouseEvent event) throws IOException {

//        SocketClient socketClient1 = SocketClient.getInstance();
//        JSONObject json1 = new JSONObject();
//        String dist1 = "adminProfile";
//        JSONObject serverResponse1 = socketClient1.socketSendReceiveJSON(json1, dist1);
//        ArrayList<JSONObject> usersJsonArray = (ArrayList<JSONObject>) serverResponse1.get("ADMIN_DATA");
//        ArrayList<User> users=new ArrayList<>();
//        for(int i=0;i<usersJsonArray.size();i++) {
//
//            String firstname = String.valueOf(usersJsonArray.get(i).get("firstname"));
//            String lastname = String.valueOf(usersJsonArray.get(i).get("lastname"));
//            String balance = String.valueOf(usersJsonArray.get(i).get("Balance"));
//            String username = String.valueOf(usersJsonArray.get(i).get("username"));
//            System.out.println(firstname+" "+lastname+" "+balance+" "+username);
//
//            ArrayList<JSONObject> ordersJsonArray = (ArrayList<JSONObject>) usersJsonArray.get(i).get("Orders");
//            ArrayList<Order> orders = new ArrayList<>();
//            for (int k = 0; k < ordersJsonArray.size(); k++) {
//                String order_id = (String) ordersJsonArray.get(k).get("order-id");
//                String total_money = (String) ordersJsonArray.get(k).get("order-total-money");
//                String number_of_products = (String) ordersJsonArray.get(k).get("order-num-of-products");
//
//                ArrayList<JSONObject> orderProductsArray = (ArrayList<JSONObject>) ordersJsonArray.get(k).get("order-products");
//                ArrayList<Product> products = new ArrayList<>();
//
//                for (int j = 0; j < orderProductsArray.size(); j++) {
//                    String name = (String) orderProductsArray.get(j).get("product-name");
//                    String quantity = (String) orderProductsArray.get(j).get("product-qty");
//                    System.out.println(name + " " + quantity);
//                    Product product = new Product(name, quantity);
//                    products.add(product);
//
//
//                }
//
//                Order order = new Order(order_id, total_money, number_of_products, products);
//                orders.add(order);
//
//            }
//            User user=new User(username,firstname,lastname,balance,orders);
//        }

        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Signleton s=Signleton.getInstance();
        //Cart Scene
        FXMLLoader CartPageLoader = new FXMLLoader(getClass().getResource("cart.fxml"));
        Parent CartPane = CartPageLoader.load();
        Scene CartScene = new Scene(CartPane, 1520, 720);
        primaryStage.setScene(CartScene);
    }
    @FXML
    private ComboBox<Integer> itemCount;
    @FXML
    private VBox chosenItemCard;

    @FXML
    private Label ItemNameLable;
    @FXML
    private Label ItemCategoryid;
    @FXML
    private Label ItemPriceLabel;

    @FXML
    private ImageView ItemImg;

    @FXML
    private ScrollPane scroll;

    @FXML
    private GridPane grid;
    @FXML
    private TextField searchedItem;
    @FXML
    private TextField categorytext;
    @FXML
    private Label ItemCategoryLabel;

    private List<Item> items = new ArrayList<>();
    private Image image;
    private MyListener myListener;

    private List<Item> getData() {
        List<Item> items = new ArrayList<>();
        Item item;
        item = new Item();
        item.setName("IPhone 11");
        item.setPriceString("50");
        item.setPrice(50);
        item.setImgSrc("/img/1.png");
        item.setColor("6A7324");
        item.setCategory("mobile");
        item.setId(1);
        items.add(item);
////////////////////////////////////////////

        item = new Item();
        item.setName("IPhone 12");
        item.setPriceString("75");
        item.setId(2);
        item.setPrice(75);
        item.setImgSrc("/img/2.png");
        item.setColor("A7745B");
        item.setCategory("mobile");
        items.add(item);
//////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 13");
        item.setPriceString("100");
        item.setPrice(100);
        item.setId(3);
        item.setImgSrc("/img/3.png");
        item.setColor("F16C31");
        item.setCategory("mobile");
        items.add(item);
/////////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 14");
        item.setPriceString("100");
        item.setPrice(100);
        item.setId(4);
        item.setImgSrc("/img/4.png");
        item.setColor("291D36");
        item.setCategory("mobile");
        items.add(item);
//////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 15");
        item.setPriceString("120");
        item.setPrice(120);
        item.setId(5);
        item.setImgSrc("/img/5.png");
        item.setColor("22371D");
        item.setCategory("mobile");
        items.add(item);
////////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 16");
        item.setPriceString("150");
        item.setPrice(150);
        item.setId(6);
        item.setImgSrc("/img/6.png");
        item.setColor("FB5D03");
        item.setCategory("mobile");
        items.add(item);
///////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 17");
        item.setPriceString("199");
        item.setPrice(199);
        item.setId(7);
        item.setImgSrc("/img/7.png");
        item.setColor("80080C");
        item.setCategory("mobile");
        items.add(item);
/////////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 18");
        item.setPriceString("200");
        item.setPrice(200);
        item.setId(8);
        item.setImgSrc("/img/8.png");
        item.setColor("FFB605");
        item.setCategory("laptop");
        items.add(item);
//////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 19");
        item.setPriceString("299");
        item.setPrice(299);
        item.setId(9);
        item.setImgSrc("/img/9.png");
        item.setColor("5F060E");
        item.setCategory("laptop");
        items.add(item);
//////////////////////////////////////////////
        item = new Item();
        item.setName("IPhone 20");
        item.setPriceString("399");
        item.setPrice(399);
        item.setId(10);
        item.setImgSrc("/img/10.png");
        item.setColor("E7C00F");
        item.setCategory("laptop");
        items.add(item);

        return items;
    }
    @FXML
    private void searchByName(ActionEvent event){
        List<Item> items = new ArrayList<>();
        items=getData();

        Item searched=new Item();
        String searchedItemName=searchedItem.getText();

        for(int i=0;i<items.size();i++){
            if(items.get(i).getName().equalsIgnoreCase(searchedItem.getText())){
                searched= items.get(i);
            }
        }
        if(searched.getName()==null){
            return;
        }
        setChosenItem(searched);

    }
    @FXML
    private void searchByCategory(ActionEvent event){
        grid.getChildren().clear();
        List<Item> items = new ArrayList<>();
        items=getData();

        List<Item> fetched=new ArrayList<>();


        String searchedItemCategory=categorytext.getText();
        for(int i=0;i< items.size();i++){
            if(items.get(i).getCategory().equals(searchedItemCategory)){
                fetched.add(items.get(i));
            }
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < fetched.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Shopping/Controllers/item.fxml"));

                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(fetched.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        
    }
    @FXML
    private void addToCart(ActionEvent event){
        Item addedItem=new Item();
        addedItem.setName(ItemNameLable.getText());
        addedItem.setPriceString(ItemPriceLabel.getText());
        StringBuilder sb = new StringBuilder(ItemPriceLabel.getText());
        sb.deleteCharAt(0);
        addedItem.setPrice(Integer.parseInt((String.valueOf(sb))));
        addedItem.setItemCartCount(itemCount.getValue());
        addedItem.setCategory(ItemCategoryLabel.getText());
        addedItem.setId(Integer.parseInt(ItemCategoryid.getText()));

        addedItems.add(addedItem);
        s.getCartItems().add(addedItem);
        System.out.println("Item added");

    }

    private void setChosenItem(Item item) {
        ItemNameLable.setText(item.getName());
        ItemPriceLabel.setText(Main.CURRENCY + item.getPriceString());
        ItemCategoryLabel.setText(item.getCategory());
        ItemCategoryid.setText(String.valueOf(item.getId()));
        image = new Image(getClass().getResourceAsStream(item.getImgSrc()));
        ItemImg.setImage(image);
        chosenItemCard.setStyle("-fx-background-color: #" + item.getColor() + ";\n" +
                "    -fx-background-radius: 30;");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        itemCount.getSelectionModel().selectFirst();
        searchedItem.setFocusTraversable(false);
        categorytext.setFocusTraversable(false);
        items.addAll(getData());
        if (items.size() > 0) {
            setChosenItem(items.get(0));
            myListener = new MyListener() {
                @Override
                public void onClickListener(Item item) {
                    setChosenItem(item);
                }
            };
        }
        int column = 0;
        int row = 1;
        try {
            for (int i = 0; i < items.size(); i++) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setLocation(getClass().getResource("/Shopping/Controllers/item.fxml"));
                AnchorPane anchorPane = fxmlLoader.load();

                ItemController itemController = fxmlLoader.getController();
                itemController.setData(items.get(i),myListener);

                if (column == 3) {
                    column = 0;
                    row++;
                }

                grid.add(anchorPane, column++, row); //(child,column,row)
                //set grid width
                grid.setMinWidth(Region.USE_COMPUTED_SIZE);
                grid.setPrefWidth(Region.USE_COMPUTED_SIZE);
                grid.setMaxWidth(Region.USE_PREF_SIZE);

                //set grid height
                grid.setMinHeight(Region.USE_COMPUTED_SIZE);
                grid.setPrefHeight(Region.USE_COMPUTED_SIZE);
                grid.setMaxHeight(Region.USE_PREF_SIZE);

                GridPane.setMargin(anchorPane, new Insets(10));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
