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
import javafx.util.StringConverter;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class orderController implements Initializable {

    @FXML
    private TableColumn<Order, String> orderID;

    @FXML
    private TableColumn<Order, String> orderNoOfProducts;

    @FXML
    private TableColumn<Order, ArrayList<Product>> orderProducts;

    @FXML
    private TableColumn<Order, String> orderTotalMoney;
    @FXML
    private TableColumn<Order, String> products1;


    @FXML
    private TableView<Order> orderTableView;
//    StringBuffer sb = new StringBuffer();
//    public String buildStringOrder(ArrayList<Order> arrayOfOrdersForUser)
//    {
//        for(int i=0;i<arrayOfOrdersForUser.size();i++)
//        {
//            sb.append("(");
//            sb.append("Order ID: " +arrayOfOrdersForUser.get(i).getId()+"\n");
//            sb.append("Number Of products: " +arrayOfOrdersForUser.get(i).getNumber_of_products() + "\n");
//            sb.append("Order Total Money: " +arrayOfOrdersForUser.get(i).getTotal_money());
//            sb.append(") \n");
//        }
//        return sb.toString();
//    }
//    userController userController = new userController();

//    ArrayList<Order> arrayOfOrders = new ArrayList<Order>();
//    ArrayList<Order> arrayOfOrders2 = new ArrayList<Order>();

    //arrayofOrders for first user and arrayOfOrders2 for second user
//    public int fillArrayOrders()
//    {
//        for(int i=1;i<3;i++)
//        {
//
//            arrayOfOrders2.add(new Order("Order#"+i*2,""+i*400*3,""+i*4));
//
//        }
//        arrayOfOrders.add(new Order("Order#A",""+400*2,""+500*2));
//        arrayOfOrders.add(new Order("Order#B",""+400*3,""+500*3));
//
//        return arrayOfOrders.size();
//    }
    ObservableList<Order> data_table = FXCollections.observableArrayList();

    public void fillData_Table (ArrayList<User> arrayGiven)
    {
        userController userController = new userController();
        for(int i=0;i<arrayGiven.size();i++)
        {
            for(int j=0;j<arrayGiven.get(i).getOrders().size();j++)
            {
                data_table.add(new Order(arrayGiven.get(i).getOrders().get(j).getId(),
                        arrayGiven.get(i).getOrders().get(j).getTotal_money(),
                        arrayGiven.get(i).getOrders().get(j).getNumber_of_products(),
                        arrayGiven.get(i).getOrders().get(j).setProductsString()));
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

    public void initTable(){
        initCols();
    }
    public void initCols(){
        orderID.setCellValueFactory(new PropertyValueFactory<>("id"));
        orderTotalMoney.setCellValueFactory(new PropertyValueFactory<>("total_money"));
        orderNoOfProducts.setCellValueFactory(new PropertyValueFactory<>("number_of_products"));
        products1.setCellValueFactory(new PropertyValueFactory<>("productsString"));
    }
    public void loadData(ObservableList<Order> ordersList){
        orderTableView.setItems(ordersList);
    }

}
