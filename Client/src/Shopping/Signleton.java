package Shopping;

import Shopping.Controllers.CartController;

import java.util.ArrayList;

public final class Signleton {

    ArrayList<Item> cartItems = new ArrayList<Item>();
    ArrayList<User> users=new ArrayList<>();

    public ArrayList<User> getUsers() {
        return users;
    }

    public void setUsers(ArrayList<User> users) {
        this.users = users;
    }

    private String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }



    private final static Signleton INSTANCE = new Signleton();


    public ArrayList<Item> getCartItems() {
        return cartItems;
    }

    public void setCartItems(ArrayList<Item> cartItems) {
        this.cartItems = cartItems;
    }

    public static Signleton getInstance() {
        return INSTANCE;
    }

    private Signleton() {}

}
