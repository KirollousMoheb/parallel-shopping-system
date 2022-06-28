package Shopping.Controllers;

import Shopping.Signleton;
import Shopping.SocketClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;


public class LoginController {

    private Scene SignupScene;
    private Scene ShoppingScene;


    @FXML
    private TextField userName;

    @FXML
    private TextField passwordField;

    @FXML
    private Label logInMessageLabel;


    public void setSignupScene(Scene scene) {
        SignupScene = scene;
    }
    public void setShoppingScene(Scene scene) {
        ShoppingScene = scene;
    }


    public void openSignupScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(SignupScene);
    }


    public void loginButtonOnAction(ActionEvent e) throws IOException {

        if(userName.getText().isBlank() == true || passwordField.getText().isBlank() == true)
        {
            logInMessageLabel.setText("UserName and Password are Required");
        }
        else
        {
            for(int i=0; i<userName.getLength() ;i++)
            {
                if (((int)userName.getText().charAt(i) < 65 && ((int)userName.getText().charAt(i) < 48 || (int)userName.getText().charAt(i) > 57 ) ) ||(((int)userName.getText().charAt(i) > 90 )&&((int)userName.getText().charAt(i)<97)) || ((int)(int)userName.getText().charAt(i))>122 )
                {
                    logInMessageLabel.setText("Username Can't have special Characters");
                }else{
                    logInMessageLabel.setText("");

                }
            }

        }

        if(logInMessageLabel.getText().isBlank()){


            SocketClient socketClient = SocketClient.getInstance();
            JSONObject json = new JSONObject();


            json.put("username", userName.getText());
            json.put("password", passwordField.getText());



            String dist = "login";
            JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
            String db_response= (String) serverResponse.get("result");

            if(db_response.equals("user_success")){

                Signleton s=Signleton.getInstance();
                s.setUsername(userName.getText());


                Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();
                //Market Screen
                FXMLLoader MarketPaneLoader = new FXMLLoader(getClass().getResource("market.fxml"));
                Parent MarketPane = MarketPaneLoader.load();
                Scene MarketScene = new Scene(MarketPane, 1520, 855);
                primaryStage.setScene(MarketScene);

            }else if(db_response.equals("admin_success")){
                Stage primaryStage = (Stage)((Node)e.getSource()).getScene().getWindow();

            }
            else{
            logInMessageLabel.setText("Wrong Username or Password");

            }

        }
        else{
            System.out.println("Login Not Validated");

        }


    }



}
