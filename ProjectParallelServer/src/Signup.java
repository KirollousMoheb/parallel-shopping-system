import org.json.simple.JSONObject;



public class Signup {
    private String user_name;
    private String password;
    private String firstname;
    private String lastname ;



    public Signup(JSONObject req)
    {
        this.user_name= (String) req.get("username");  // hatfe2 m3 el front 3ala username w password
        this.password= (String) req.get("password");
        this.firstname=(String)req.get("firstname");
        this.lastname=(String)req.get("lastname");

    }
    public JSONObject signup()
    {   String s2 ;
        JSONObject jsonObject=new JSONObject();
        Database database = new Database();


        try {
            s2=database.Connect("select * from users where user_name = \""+this.user_name+"\"","user_name");
            if (s2=="ERROR") {

                database.insert("insert into users (user_name,password,first_name,last_name,balance,type ) values ( \""+this.user_name+"\" , \""+this.password+"\" ,\""+this.firstname+"\", \""+this.lastname+"\",2000,1) " );
                jsonObject.put("result", "success");  // hatfe2 m3 el front 3ala result
            }
            else
            {
                jsonObject.put("result","failure");
            }
        }catch (Exception e )
        {
            jsonObject.put("result","ERROR!!");
        }


        return jsonObject;
    }




}
