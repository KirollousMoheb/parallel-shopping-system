package Shopping;

public class SingleOrderHistory {
    private String productnumber;
    private String productname;
    private String quantity;

    public SingleOrderHistory(String productnumber, String productname, String quantity){
        this.productnumber = productnumber;
        this.productname = productname;
        this.quantity = quantity;
    }

    public String getProduct_name() {
        return productname;
    }

    public String getProduct_number() {
        return productnumber;
    }

    public String getQuantity() {
        return quantity;
    }
}
