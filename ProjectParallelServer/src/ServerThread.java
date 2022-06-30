import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.*;

public class ServerThread extends Thread{

    private int clientNo;
    private Socket socket;
    private DataInputStream input;
    private DataOutputStream output;

    public ServerThread(Socket socket, int clientNo) {
        this.clientNo = clientNo;
        try {
            this.socket = socket;
            input = new DataInputStream(socket.getInputStream());
            output = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String socketReceive(){
        try {
            return input.readUTF();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private void socketSend(String data){
        try {
            output.writeUTF(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public JSONObject socketReceiveJSON(){
        JSONParser parser = new JSONParser();
        JSONObject receivedJson = new JSONObject();
        try {
            receivedJson = (JSONObject) parser.parse(socketReceive());
            return (JSONObject) receivedJson.get("req");
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void socketSendJSON(JSONObject json)
    {
        JSONObject sentJson = new JSONObject();
        sentJson.put("res", json);
        socketSend(sentJson.toJSONString());
    }

    public JSONObject handleRequest(JSONObject req)
    {
        JSONObject res = new JSONObject();
        String dist = (String) req.get("@dist");
        switch (dist){
            case "test-data":
                res.put("data", req.get("data"));
                break;
            case "Sign_up":
                Signup sign_up=new Signup(req);
                res= sign_up.signup();
                break;
            case "login":
                Login login=new Login(req);
                res=login.Loginto();
                break;
            case "userProfile":
                UserProfile userProfile=new UserProfile(req);
                res=userProfile.userdata();
                break;
            case "adminProfile":
                AdminData adminData=new AdminData();
                res=adminData.getAdminData();
                break;
            case "getBalance":
                BalanceHandler balanceHandler = new BalanceHandler();
                String user_name = (String) req.get("user_name");
                int balance = balanceHandler.getBalance(user_name);
                res.put("balance",balance);
                break;
            case "addBalance":
                // Mark

                BalanceHandler balanceHandler2 = new BalanceHandler();

                String user_name2 = (String) req.get("user_name");
                System.out.println(user_name2);

                int amount = ((Long)req.get("amount")).intValue();
                System.out.println(amount);


                int new_balance = balanceHandler2.addBalance(amount,user_name2);
                res.put("new_balance",new_balance);
                System.out.println("new balance :" + new_balance);
                break;
            case"addOrder":
                // Mark
                OrderHandler handler = new OrderHandler(req);
                handler.addOrder();
        }
        return res;
    }

    @Override
    public void run(){
        System.out.println("Started serving client # " + this.clientNo);
        String line = "";
        while (true)
        {
            try
            {
                JSONObject json = socketReceiveJSON();
                JSONObject outJson = new JSONObject();

                if(json.get("@end") != null){    // close server
                    outJson.put("@end", "end");
                    socketSendJSON(outJson);
                    break;
                }

                outJson = handleRequest(json);
                socketSendJSON(outJson);
            }
            catch(Exception e)
            {
                System.out.println("Connection Error with client # " + this.clientNo);
                line = "CLOSE";
            }
        }
        try {
            input.close();
            output.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("Connection is closed with client # " + this.clientNo);
    }
}