import java.util.ArrayList;

public class Order {
    String id;
    String total_money;
    String number_of_products;
    ArrayList<Product> products=new ArrayList<>();

    public Order(String id, String total_money, String number_of_products) {
        this.id = id;
        this.total_money = total_money;
        this.number_of_products = number_of_products;
    }

    public Order(String id, String total_money, String number_of_products, ArrayList<Product> products) {
        this.id = id;
        this.total_money = total_money;
        this.number_of_products = number_of_products;
        this.products = products;
    }

    public String getId() {
        return id;
    }

    public String getTotal_money() {
        return total_money;
    }

    public String getNumber_of_products() {
        return number_of_products;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setTotal_money(String total_money) {
        this.total_money = total_money;
    }

    public void setNumber_of_products(String number_of_products) {
        this.number_of_products = number_of_products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }
}