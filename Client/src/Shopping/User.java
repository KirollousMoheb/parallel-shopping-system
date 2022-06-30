package Shopping;

import java.util.ArrayList;

public class User {
    private String username;
    private String first_name;
    private String last_name;
    private String balance;
    private String totalPayment;


    public String getOrdersString() {
        return ordersString;
    }

    private String ordersString;
    private ArrayList<Order> orders;





    public User( String first_name, String last_name, String balance,ArrayList<Order> orders) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.balance = balance;
        this.orders = orders;

    }
    public User( String first_name, String last_name, String balance,String ordersString,String totalPayment) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.balance = balance;
        this.ordersString = ordersString;
        this.totalPayment = totalPayment;
    }
    public User(String username)
    {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLast_name() {
        return last_name;
    }

    public void setLast_name(String last_name) {
        this.last_name = last_name;
    }

    public String getBalance() {
        return balance;
    }

    public void setBalance(String balance) {
        this.balance = balance;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }


    public void setOrders(ArrayList<Order> orders) {

        this.orders = orders;
    }
    public String setOrdersString() {

        StringBuffer sb = new StringBuffer();
        for(int i=0;i<orders.size();i++)
        {
            sb.append(i+1+"- (");
            sb.append("Order ID: " +orders.get(i).getId()+"\n");
            sb.append("Number Of products: " +orders.get(i).getNumber_of_products() + "\n");
            sb.append("Order Total Money: " +orders.get(i).getTotal_money()+")"+"\n");

           // sb.append("Products: " +orders.get(i).products.get(i).getName());
                    }
        return sb.toString();

    }
    public String setTotalPayment() {
        int total =0;
        String totalString= "";
        for(int i=0;i<orders.size();i++)
        {
            totalString = orders.get(i).getTotal_money();
            total += Integer.valueOf(totalString);
        }


        return String.valueOf(total);
    }

    public String getTotalPayment() {
        return totalPayment;
    }

    public User() {
    }

    public User(String username, String first_name, String last_name, String balance, ArrayList<Order> orders) {
        this.username = username;
        this.first_name = first_name;
        this.last_name = last_name;
        this.balance = balance;
        this.orders = orders;
    }





}
