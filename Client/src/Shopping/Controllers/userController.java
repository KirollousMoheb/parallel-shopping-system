package Shopping.Controllers;

import Shopping.*;
import javafx.beans.property.ReadOnlyListWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.skin.TableHeaderRow;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.util.Callback;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class userController implements Initializable {
    @FXML
    private Label Target;

    @FXML
    private Label adminName;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button overviewBtn;
    @FXML
    private Button orderBtn;
    @FXML
    private Button productBtn;

    @FXML
    private Button userBtn;

    @FXML
    private TableView<User> usersTable;
    @FXML
    private TableColumn<User, String> balance;

    @FXML
    private TableColumn<User, String> first_name;

    @FXML
    private TableColumn<User, String> last_name;
    @FXML
    private TableColumn<User, String> orders1;
    public String ordersTotalPayment;
    @FXML
    private TableColumn<ArrayList, Order> orders;
    @FXML
    private GridPane usersPane;
    @FXML
    private GridPane overviewPane;
    @FXML
    private GridPane usersTableGrid;


    private Stage stage;
    private Scene scene;
//    Order orderInvoked = new Order();
    ArrayList<Order> o1 = new ArrayList<Order>();

    public ArrayList<User> getArrayOfUsers() {
        return arrayOfUsers;
    }

    public void setArrayOfUsers(ArrayList<User> arrayOfUsers) {
        this.arrayOfUsers = arrayOfUsers;
    }

    ArrayList<User> arrayOfUsers = new ArrayList<User>();
    Signleton s =Signleton.getInstance();
    User admin = new User(s.getUsername());
    public String getAdminName() throws IOException {
        return admin.getUsername();

    }
    orderController orderController = new orderController();
    ArrayList<Order> arrayOfOrders = new ArrayList<Order>();
    ArrayList<Order> arrayOfOrders2 = new ArrayList<Order>();
    ArrayList<Product> orderAProducts  =new ArrayList<Product>();
    ArrayList<Product> orderBProducts = new ArrayList<Product>();
    ArrayList<Product> order1Products  =new ArrayList<Product>();
    ArrayList<Product> order2Products = new ArrayList<Product>();

    public int fillArrayOrders()
    {
        order1Products.add(new Product("Product 1A","70"));
        order1Products.add(new Product("Product 1B","80"));
        order2Products.add(new Product("Product 2A","77"));
        order2Products.add(new Product("Product 2B","88"));
        orderAProducts.add(new Product("Product 3A","48"));
        orderAProducts.add(new Product("Product 3B","98"));
        orderBProducts.add(new Product("Product 3A","48"));
        orderBProducts.add(new Product("Product 3B","98"));

        arrayOfOrders2.add(new Order("Order#1",""+400*3,""+4,order1Products));
        arrayOfOrders2.add(new Order("Order#2",""+400*3,""+4,order2Products));
        arrayOfOrders.add(new Order("Order#A",""+400*2,""+500*2,orderAProducts));
        arrayOfOrders.add(new Order("Order#B",""+400*3,""+500*3,orderBProducts));

        return arrayOfOrders.size();
    }

    public int fillArray()
    {
       fillArrayOrders();

        //user one
        arrayOfUsers.add(new User("Userr ","User " + 1,"1500",
                             arrayOfOrders));
        //user two
        arrayOfUsers.add(new User("Userr","User " + 2, "1600" ,
                                     arrayOfOrders2));

    return arrayOfUsers.size();
    }
    ObservableList<User> data_table = FXCollections.observableArrayList();
    public String totalPayments(ArrayList<Order> ordersInput)
    {
        int total =0;
        String totalString= "";
        for(int i=0;i<ordersInput.size();i++)
        {
            totalString = ordersInput.get(i).getTotal_money();
            total += Integer.valueOf(totalString);
        }

        return String.valueOf(total);
    }
    public void fillData_Table (ArrayList<User> arrayGiven)
        {
            for(int i=0;i<arrayGiven.size();i++)
            {
                data_table.add(new User(arrayGiven.get(i).getFirst_name(),
                        arrayGiven.get(i).getLast_name(),
                        arrayGiven.get(i).getBalance(),
                        arrayGiven.get(i).setOrdersString(),
                        arrayGiven.get(i).setTotalPayment()));
            }
        }

    @FXML
    private HBox paneTry;

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


        fillData_Table(users);

        int rows = arrayOfUsers.size();
        TableHeaderRow headerRow = (TableHeaderRow) usersTable.lookup("TableHeaderRow");
        double tableHeight = (rows * usersTable.getFixedCellSize())
                // add the insets or we'll be short by a few pixels
                + usersTable.getInsets().getTop() + usersTable.getInsets().getBottom()
                // header row has its own (different) height
                + (headerRow == null ? 0 : headerRow.getHeight())
                ;

        usersTable.setMinHeight(tableHeight);
        usersTable.setMaxHeight(tableHeight);
        usersTable.setPrefHeight(tableHeight);
//        System.out.println(data_table.get(0).setTotalPayment());
        loadData(data_table);
        System.out.println(usersNumber());
        initTable();

    }

    public int getTotalBalance()
    {
        int balances =0;
        for(int i=0;i<data_table.size();i++)
        {
            balances += Integer.valueOf(data_table.get(i).getBalance());
        }
        return balances;
    }

    @FXML
    public GridPane overviewGrid;
    public void logout(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //Market Screen
        FXMLLoader LoginPaneLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent LoginPane = LoginPaneLoader.load();
        Scene LoginScene = new Scene(LoginPane, 850, 500);
        primaryStage.setScene(LoginScene);
;
    }
    public int usersNumber()
    {
        return arrayOfUsers.size();
    }
    public int ordersNumber(){
        return o1.size();
    }
    public void initTable(){
        initCols();
    }
    public void initCols(){
        first_name.setCellValueFactory(new PropertyValueFactory<>("first_name"));
        last_name.setCellValueFactory(new PropertyValueFactory<>("last_name"));
        balance.setCellValueFactory(new PropertyValueFactory<>("balance"));
        orders.setCellValueFactory(new PropertyValueFactory<>("ordersString"));
        orders1.setCellValueFactory(new PropertyValueFactory<>("totalPayment"));

    }
    public void loadData(ObservableList<User> usersList){
        usersTable.setItems(usersList);

    }
    private Scene firstScene;
    public void setOverviewScene(Scene scene1) {
        firstScene = scene;
    }
    public void openFirstScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(firstScene);
    }
}
