package Shopping.Controllers;

import Shopping.Product;
import Shopping.SingleOrderHistory;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class OrderDataTable {

    @FXML
    private TableColumn<SingleOrderHistory, String> Product_name;

    @FXML
    private TableColumn<SingleOrderHistory, String> Product_number;

    @FXML
    private TableColumn<SingleOrderHistory, String> Quantity;

    @FXML
    private HBox TotalOrderData;

    @FXML
    private HBox hinside;

    @FXML
    private Label orderId;

    @FXML
    private TableView<SingleOrderHistory> table;

    @FXML
    private Label totalmoney;

    @FXML
    private VBox vboxx;

    ObservableList<SingleOrderHistory> list = FXCollections.observableArrayList();

    private void setTableData(ArrayList<Product> arr){

        int size = arr.size();
            for(int i = 0;i<size;i++){
                list.add(new SingleOrderHistory(Integer.toString(i+1),arr.get(i).getName(),arr.get(i).getQuantity()));
            }
          Quantity.setCellValueFactory(new PropertyValueFactory<SingleOrderHistory,String>("Quantity"));
          Product_number.setCellValueFactory(new PropertyValueFactory<SingleOrderHistory,String>("Product_number"));
          Product_name.setCellValueFactory(new PropertyValueFactory<SingleOrderHistory,String>("Product_name"));
          table.setItems(list);
    }
    public void setData(String order_id, String total_money,ArrayList<Product> arr){
        this.orderId.setText("#"+order_id);
        this.totalmoney.setText(total_money);
        setTableData(arr);

    }
}
