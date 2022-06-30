import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class OrderHandler {

    JSONObject req;

    public OrderHandler(JSONObject req)
    {
        this.req = req;
    }

    public void addOrder()
    {
        try
        {
            String user_name = (String) req.get("user_name");
            ArrayList<Long> products_ids = (ArrayList<Long>) req.get("products_ids");
            ArrayList<Long> quantities = (ArrayList<Long>) req.get("quantities");
            long total_price = (long)req.get("total_price");
            long number_of_products = 0;
            for(int i =0 ; i < quantities.size();i++)
            {
                number_of_products += (quantities.get(i));
            }
            // get user id from users table
            Database database = new Database();
            String query1 = "SELECT id FROM users WHERE user_name = '" + user_name + "';";
            System.out.println(query1);
            List<HashMap<String,Object>> list_users = database.ConnectSelect(query1);
            int user_id = (int)list_users.get(0).get("id");
            System.out.println("user id : "+user_id);
            System.out.println("user name : "+user_name);
            System.out.println("quantities arr : "+quantities);
            System.out.println("total price : " + total_price);
            // data is ready
            // insert in (orders table) :
            String query2 = "INSERT INTO orders (user_id,total_money,number_of_products) VALUES ("+
                    user_id + "," +
                    total_price + "," +
                    number_of_products +
                    ");";
            database.ConnectInsert(query2);

            // get order_id : (orders table)
            String query3 = "SELECT id FROM orders WHERE user_id =" + user_id + ";";
            List<HashMap<String,Object>> list = database.ConnectSelect(query3);
            int order_id = (int)list.get(list.size()-1).get("id");
            System.out.println("order id : "+order_id);

            // insert every product in (orders_products table)
            for(int i = 0 ; i < products_ids.size();i++)
            {
                String query4 = "INSERT INTO orders_products (order_id,product_id,quantity) VALUES ("+
                        order_id + "," +
                        products_ids.get(i) +","+
                        quantities.get(i) + ");";
                database.ConnectInsert(query4);
            }

            // update user's balance (users table)
            String query5 = "UPDATE users "+
                    "SET balance = balance - " + total_price + " WHERE id = "+user_id+";";
            database.ConnectInsert(query5);



        }

        catch (Exception err)
        {
            System.out.println(err);
            System.out.println("error in order handler");
        }
    }
}
