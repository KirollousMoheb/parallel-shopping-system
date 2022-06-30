
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Database
{   private String query;
    private String col_name ;

    public Database()
    {

    }
    public Database(String Q, String namecol)
    {
        this.query = Q;
        this.col_name = namecol;
    }

    public String  Connect(String Query,String col_name )
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(Query);

            while (resultset.next())
            {
                return  resultset.getString(col_name);
            }
        }catch (Exception e )
        {
            e.printStackTrace();

        }
     return "ERROR";
    }
    public void getUsersNames(String Query, ArrayList<String> usernames){
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(Query);

            while (resultset.next())
            {
                usernames.add(resultset.getString("user_name"));
            }
        }catch (Exception e )
        {
            e.printStackTrace();

        }
    }

    public void connectUser(String Query,String values[] )
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(Query);

            while (resultset.next())
            {

                values[0]=resultset.getString("user_name");
                values[1]=resultset.getString("first_name");
                values[2]=resultset.getString("last_name");
                values[3]=resultset.getString("balance");
            }
        }catch (Exception e )
        {
            e.printStackTrace();

        }
    }

    public void connectOrder(String Query, ArrayList<String> orders_ids,ArrayList<String> total_money,ArrayList<String> num_of_products)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(Query);

            while (resultset.next())
            {

                orders_ids.add(resultset.getString("id"));
                total_money.add(resultset.getString("total_money"));
                num_of_products.add(resultset.getString("number_of_products"));
            }
        }catch (Exception e )
        {
            e.printStackTrace();

        }
    }
    public void connectProducts(String Query, ArrayList<Product> products )
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(Query);

            while (resultset.next())
            {

                products.add(new Product(resultset.getString("name"),resultset.getString("quantity")));

            }
        }catch (Exception e )
        {
            e.printStackTrace();

        }
    }
    public List<HashMap<String ,Object>>  ConnectSelect(String Query)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            ResultSet resultset = statement.executeQuery(Query);
            ResultSetMetaData md = resultset.getMetaData();
            int columns = md.getColumnCount();
            List<HashMap<String,Object>> list = new ArrayList<HashMap<String,Object>>();

            while (resultset.next()) {
                HashMap<String,Object> row = new HashMap<String, Object>(columns);
                for(int i=1; i<=columns; ++i) {
                    row.put(md.getColumnName(i),resultset.getObject(i));
                }
                list.add(row);
            }

            //System.out.println(list);

            return list;

        }catch (Exception e )
        {
            e.printStackTrace();

        }
        return null;
    }

    public void insert (String Query)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            statement.executeUpdate(Query);

        }catch (Exception e )
        {
            e.printStackTrace();

        }

    }

    public void ConnectInsert(String Query)
    {
        try
        {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/project", "root", "123456");
            Statement statement = connection.createStatement();
            boolean succ = statement.execute(Query);
        }
        catch (Exception err)
        {
            System.out.println(err);
            System.out.println("error in insert statment");
        }

    }
}
