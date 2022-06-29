package Shopping;

public class Item {
    private String name;
    private String imgSrc;
    private String priceString;
    private int id;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    private int itemCartCount;

    public int getItemCartCount() {
        return itemCartCount;
    }

    public void setItemCartCount(int itemCartCount) {
        this.itemCartCount = itemCartCount;
    }

    private int price;

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    private String color;
    private String Category;

    public String getCategory() {
        return Category;
    }

    public void increamentCount(){
        this.itemCartCount++;
    }
    public void decreamentCount(){
        if(this.itemCartCount>=1){
            this.itemCartCount--;

        }
    }
    public void setCategory(String category) {
        Category = category;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImgSrc() {
        return imgSrc;
    }

    public void setImgSrc(String imgSrc) {
        this.imgSrc = imgSrc;
    }

    public String getPriceString() {
        return priceString;
    }

    public void setPriceString(String priceString) {
        this.priceString = priceString;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }
}
