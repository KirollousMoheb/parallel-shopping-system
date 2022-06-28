import org.json.simple.JSONObject;

import java.util.ArrayList;

public class AdminData {

    ArrayList<String> usernames=new ArrayList<>();

    public ArrayList<String> getUsernames() {
        Database database = new Database();
        database.getUsersNames("Select user_name from users",usernames);
        return this.usernames;
    }

    public void setUsernames(ArrayList<String> usernames) {
        this.usernames = usernames;
    }

    public AdminData() {
    }


    public JSONObject getAdminData() {
        JSONObject jsonObject = new JSONObject();
        getUsernames();
        ArrayList<JSONObject> usersArr = new ArrayList<>();
        try {
            for (int i = 0; i < usernames.size(); i++) {
                JSONObject user = new JSONObject();
                user.put("username", usernames.get(i));
                UserProfile userProfile = new UserProfile(user);
                usersArr.add(userProfile.userdata());
            }
        }catch (Exception e){

        }
        jsonObject.put("ADMIN_DATA",usersArr);
        System.out.println(jsonObject.toJSONString());
        return jsonObject;
    }
}
