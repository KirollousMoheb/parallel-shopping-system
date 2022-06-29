package Shopping.Controllers;

import Shopping.Order;
import Shopping.Product;
import Shopping.SocketClient;
import Shopping.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class productController implements Initializable {
    @FXML
    private Label Target;

    @FXML
    private Label adminName;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableView<Product> prodcutsTable;
    @FXML
    private TableColumn<Product, String> productName;

    @FXML
    private TableColumn<Product, String> productQuantity;

    ObservableList<Product> data_table = FXCollections.observableArrayList();
    public void fillData_Table (ArrayList<User> arrayGiven)
    {
        for(int i=0;i<arrayGiven.size();i++)
        {
            for(int j=0;j<arrayGiven.get(i).getOrders().size();j++)
            {
                for(int k=0;k<arrayGiven.get(i).getOrders().get(j).getProducts().size();k++)
                data_table.add(new Product(arrayGiven.get(i).getOrders().get(j).getProducts().get(k).getName(),
                        arrayGiven.get(i).getOrders().get(j).getProducts().get(k).getQuantity()));
            }
        }
    }
    private Stage stage;
    @FXML
    public GridPane overviewGrid;
    public void logout(ActionEvent event) throws IOException {
        Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
        //Market Screen
        FXMLLoader LoginPaneLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
        Parent LoginPane = LoginPaneLoader.load();
        Scene LoginScene = new Scene(LoginPane, 850, 500);
        primaryStage.setScene(LoginScene);
    }
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
        initTable();
        loadData(data_table);
    }
    public int getProductsNumber()
    {
        return data_table.size();
    }
    public void initTable(){
        initCols();
    }
    public void initCols(){
        productName.setCellValueFactory(new PropertyValueFactory<>("name"));
        productQuantity.setCellValueFactory(new PropertyValueFactory<>("quantity"));

    }
    public void loadData(ObservableList<Product> productsList){
        prodcutsTable.setItems(productsList);
    }
}
