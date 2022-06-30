import java.util.HashMap;
import java.util.List;

public class BalanceHandler {

    public int getBalance(String user_name)
    {
        try
        {
            Database db = new Database();
            String query = "SELECT balance FROM users WHERE user_name = '" + user_name + "';";
            List<HashMap<String,Object>> list = db.ConnectSelect(query);
            int balance = (int)list.get(0).get("balance");
            return balance;

        }
        catch(Exception err)
        {
            System.out.println(err);
        }
        return 0;
    }
    public int addBalance(int amount,String user_name)
    {
        try
        {
            Database db = new Database();
            String query = "UPDATE users SET balance=balance+ " + amount +
                    " WHERE user_name = '"+ user_name +"';";
            //System.out.println(query);
            db.ConnectInsert(query);

            String query2 = "SELECT balance FROM users WHERE user_name = '" +
                    user_name + "';";
            //System.out.println(query2);
            List<HashMap<String,Object>> list = db.ConnectSelect(query2);
            int balance = (int)list.get(0).get("balance");
            return balance;

        }
        catch(Exception err)
        {
            System.out.println(err);
        }
        return -1;
    }
}
