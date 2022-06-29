package Shopping.Controllers;

import Shopping.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class adminController implements Initializable {
    @FXML
    private Label Target;

    @FXML
    private Label adminName;
    @FXML
    private Label  CurrentBalance;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button overviewBtn;

    @FXML
    private Button productBtn;

    @FXML
    private Label totalOrders;

    @FXML
    private Label totalProducts;

    @FXML
    private Label totalUsers;
    @FXML
    private Label totalBalance;
    @FXML
    private Label adminName11;
    @FXML
    private Label adminName112;

    @FXML
    private Button userBtn;

    @FXML
    private  Button orderBtn;
    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> balance;

    @FXML
    private TableColumn<User, String> first_name;

    @FXML
    private TableColumn<User, String> last_name;

    @FXML
    private TableColumn<User, ArrayList<Order>> orders;

    @FXML
    private GridPane usersPane;
    @FXML
    private GridPane overviewPane;
    @FXML
    private GridPane productsPane;
    @FXML
    private GridPane usersTableGrid;
    @FXML
    public GridPane overviewGrid;

    private Stage stage;
    private Scene scene;


    @FXML
    private Label xx;


    @FXML
    private Label totalIncome;
    @FXML
    private Label totalOrdersLabel;



    //xD
    public void buttonPressing(Button btn)
    {
        if(btn == userBtn)
        {
            pressButton(userBtn);
            unPressButton(productBtn);
            unPressButton(overviewBtn);
            unPressButton(orderBtn);
        } else if (btn == productBtn) {
            pressButton(productBtn);
            unPressButton(userBtn);
            unPressButton(overviewBtn);
            unPressButton(orderBtn);
        }
        else if (btn == orderBtn) {
            pressButton(orderBtn);
            unPressButton(productBtn);
            unPressButton(overviewBtn);
            unPressButton(userBtn);
        }
        else if (btn == overviewBtn) {
            pressButton(overviewBtn);
            unPressButton(productBtn);
            unPressButton(orderBtn);
            unPressButton(userBtn);
        }

    }
    public void pressButton(Button btn)
    {
        btn.setStyle("-fx-background-color : #1620A1; -fx-background-radius: 10;");

    }
    public void unPressButton(Button btn)
    {
        btn.setStyle("-fx-background-color : #05071F; -fx-background-radius: 10;");

    }
    public void switchtoUsers(ActionEvent event) throws IOException{
          GridPane users = FXMLLoader.load(getClass().getResource("Users.fxml"));
          overviewGrid.getChildren().setAll(users);
//            buttonPressing(userBtn);


    }
    public void switchtoOverView(ActionEvent event) throws IOException{
          GridPane users = FXMLLoader.load(getClass().getResource("adminView.fxml"));
          overviewGrid.getChildren().setAll(users.getChildren().get(1));
//          buttonPressing(overviewBtn);
    }
    public void switchtoProducts(ActionEvent event) throws IOException{
        GridPane users = FXMLLoader.load(getClass().getResource("Products.fxml"));
        overviewGrid.getChildren().setAll(users);
//        buttonPressing(productBtn);
    }

    public void switchtoOrders(ActionEvent event) throws IOException{
        GridPane users = FXMLLoader.load(getClass().getResource("orders.fxml"));
        overviewGrid.getChildren().setAll(users);
//        buttonPressing(orderBtn);
    }
    public void logout(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //Market Screen
        FXMLLoader LoginPaneLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent LoginPane = LoginPaneLoader.load();
        Scene LoginScene = new Scene(LoginPane, 850, 500);
        primaryStage.setScene(LoginScene);
    }
    userController userController = new userController();
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        SocketClient socketClient1 = SocketClient.getInstance();
        JSONObject json1 = new JSONObject();
        String dist1 = "adminProfile";
        JSONObject serverResponse1 = socketClient1.socketSendReceiveJSON(json1, dist1);
        ArrayList<JSONObject> usersJsonArray = (ArrayList<JSONObject>) serverResponse1.get("ADMIN_DATA");
        ArrayList<User> users=new ArrayList<>();
        for(int i=0;i<usersJsonArray.size();i++) {

            String firstname = String.valueOf(usersJsonArray.get(i).get("firstname"));
            String lastname = String.valueOf(usersJsonArray.get(i).get("lastname"));
            String balance = String.valueOf(usersJsonArray.get(i).get("Balance"));
            String username = String.valueOf(usersJsonArray.get(i).get("username"));
            System.out.println(firstname+" "+lastname+" "+balance+" "+username);

            ArrayList<JSONObject> ordersJsonArray = (ArrayList<JSONObject>) usersJsonArray.get(i).get("Orders");
            ArrayList<Order> orders = new ArrayList<>();
            for (int k = 0; k < ordersJsonArray.size(); k++) {
                String order_id = (String) ordersJsonArray.get(k).get("order-id");
                String total_money = (String) ordersJsonArray.get(k).get("order-total-money");
                String number_of_products = (String) ordersJsonArray.get(k).get("order-num-of-products");

                ArrayList<JSONObject> orderProductsArray = (ArrayList<JSONObject>) ordersJsonArray.get(k).get("order-products");
                ArrayList<Product> products = new ArrayList<>();

                for (int j = 0; j < orderProductsArray.size(); j++) {
                    String name = (String) orderProductsArray.get(j).get("product-name");
                    String quantity = (String) orderProductsArray.get(j).get("product-qty");
                    System.out.println(name + " " + quantity);
                    Product product = new Product(name, quantity);
                    products.add(product);


                }

                Order order = new Order(order_id, total_money, number_of_products, products);
                orders.add(order);

            }
            User user=new User(username,firstname,lastname,balance,orders);
            users.add(user);
        }
        totalUsers.setText(String.valueOf(users.size()));
        String totalProductss = "";
        double totalIncomeD =0;
        double totalUserBalance =0;
        int orders=0;
        int products=0;
        for(int i=0;i<users.size();i++)
        {
            totalIncomeD += Integer.valueOf(users.get(i).setTotalPayment());
            totalUserBalance += Integer.valueOf(users.get(i).getBalance());
            orders += users.get(i).getOrders().size();
        }
        for(int i=0;i<users.size();i++)
        {
            for(int j=0;j<users.get(i).getOrders().size();j++)
            {
                products += users.get(i).getOrders().get(j).getProducts().size();
            }

        }

        try {
            Signleton s =Signleton.getInstance();
            adminName112.setText(userController.getAdminName());
            adminName112.setAlignment(Pos.CENTER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        totalIncome.setText(String.valueOf(totalIncomeD + " L.E."));
        totalBalance.setText(String.valueOf(totalUserBalance + " L.E."));
        System.out.println(totalProductss);
        totalOrdersLabel.setText(String.valueOf(orders));
        totalProducts.setText(String.valueOf(products));


    }

    public String setAdminName() throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Users.fxml"));
        userController userController = new userController();
        loader.setController(userController);
        return userController.admin.getUsername();
    }
}