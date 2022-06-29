import org.json.simple.JSONObject;

import java.util.ArrayList;

public class UserProfile {
    private String username;

    public UserProfile(JSONObject req) {
        this.username = (String) req.get("username");

    }
    public UserProfile() {

    }

    public JSONObject userdata() {
        String[] values = new String[4];
        ArrayList<String> orders_ids = new ArrayList<>();
        ArrayList<String> total_money = new ArrayList<>();
        ArrayList<String> num_of_products = new ArrayList<>();
        ArrayList<Order> orders = new ArrayList<>();


        JSONObject jsonObject = new JSONObject();
        Database database = new Database();

        try {
            database.connectUser("select user_name,first_name,last_name,balance from users where user_name = \"" + this.username + "\"", values);
            System.out.println(values[0]+" "+values[1]+" "+values[2]+" "+values[3]);
            jsonObject.put("username", values[0]);
            jsonObject.put("Name", values[1] + " " + values[2]);
            jsonObject.put("firstname", values[1]);
            jsonObject.put("lastname", values[2]);

            jsonObject.put("Balance", values[3]);
            database.connectOrder("select O.id,total_money,number_of_products from  orders O, users U where U.id=user_id and user_name= \"" + this.username + "\"",
                    orders_ids,
                    total_money,
                    num_of_products);
            for (int i = 0; i < orders_ids.size(); i++) {
                orders.add(new Order(orders_ids.get(i), total_money.get(i), num_of_products.get(i)));
            }
            for (int i = 0; i < orders_ids.size(); i++) {
                ArrayList<Product> products = new ArrayList<>();
                database.connectProducts("select P.name,quantity from product P,orders_products op where op.product_id=P.id and op.order_id= \"" + orders_ids.get(i) + "\"",
                        products);
                orders.get(i).setProducts(products);
            }

            ArrayList<JSONObject> ordersJsonArray = new ArrayList<>();
            for (int i = 0; i < orders_ids.size(); i++) {
                JSONObject signleJsonOrder = new JSONObject();
                signleJsonOrder.put("order-id", orders.get(i).getId());
                signleJsonOrder.put("order-total-money", orders.get(i).getTotal_money());
                signleJsonOrder.put("order-num-of-products", orders.get(i).getNumber_of_products());

                ArrayList<JSONObject> orderProductsArray = new ArrayList<>();
                for (int j = 0; j < orders.get(i).getProducts().size(); j++) {
                    JSONObject signleJsonProduct = new JSONObject();
                    signleJsonProduct.put("product-name", orders.get(i).getProducts().get(j).getName());
                    signleJsonProduct.put("product-qty", orders.get(i).getProducts().get(j).getQuantity());
                    orderProductsArray.add(signleJsonProduct);
                }
                signleJsonOrder.put("order-products", orderProductsArray);
                ordersJsonArray.add(signleJsonOrder);
            }


            jsonObject.put("Orders", ordersJsonArray);
            System.out.println(jsonObject.toJSONString());

        } catch (Exception e) {


        }

        return jsonObject;
    }
}