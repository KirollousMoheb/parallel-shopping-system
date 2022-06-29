import org.json.simple.JSONObject;

public class Login {
private String username;
private String password;

public Login(JSONObject req)
{
    this.username= (String) req.get("username");  // hatfe2 m3 el front 3ala username w password
    this.password= (String) req.get("password");

}

public JSONObject Loginto()
{   String role,db_password ;
    JSONObject jsonObject=new JSONObject();
    Database database = new Database();


    try {
        db_password=database.Connect("select * from users where user_name = \""+this.username+"\"","password");
        role=database.Connect("select * from users where user_name = \""+this.username+"\"","type");

        if (db_password.equals(password)&&role.equals("1"))
        {
            jsonObject.put("result","user_success");
        }
        else if(db_password.equals(password)&&role.equals("0")){
            jsonObject.put("result","admin_success");

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
