public class Product {
    String quantity;
    String name;

    public Product(String name, String quantity) {
        this.name = name;
        this.quantity = quantity;

    }

    public String getQuantity() {
        return quantity;
    }

    public String getName() {
        return name;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }
}