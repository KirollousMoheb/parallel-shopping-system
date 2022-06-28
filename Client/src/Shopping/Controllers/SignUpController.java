package Shopping.Controllers;

import Shopping.SocketClient;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.text.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import org.json.simple.JSONObject;

import java.io.IOException;

public class SignUpController {

    private Scene LoginScene;

    @FXML
    private TextField firstName;

    @FXML
    private TextField lastName;

    @FXML
    private PasswordField pass1;

    @FXML
    private PasswordField pass2;

    @FXML
    private Text wrongName;

    @FXML
    private Text wrongPass;


    @FXML
    private Text wrongpass1;

    @FXML
    private Text userWrong;

    @FXML
    private TextField username;

    public void setLoginScene(Scene scene) {
        LoginScene = scene;
    }

    public void openFirstScene(ActionEvent actionEvent) {
        Stage primaryStage = (Stage)((Node)actionEvent.getSource()).getScene().getWindow();
        primaryStage.setScene(LoginScene);
    }

    @FXML
    void check(ActionEvent event) throws IOException {

        //First and Last Name Validation
        if(firstName.getText().isBlank() == true){
            wrongName.setText("First Name is Required");
        }
         else if(lastName.getText().isBlank() == true){
            wrongName.setText("Last Name is Required");

        }else{
            for(int i=0; i<(firstName.getText().length()); i++){

                if((int)firstName.getText().charAt(i) < 65  ||(((int)firstName.getText().charAt(i) > 90 )&&((int)firstName.getText().charAt(i)<97)) || ((int)(int)firstName.getText().charAt(i))>122 ){

                    wrongName.setText("Names  Can't have special Characters Or Numbers");

                }

                else{
                    wrongName.setText("");
                    for(int j=0; j<(lastName.getText().length()); j++){

                        if(((int)lastName.getText().charAt(j) < 65 ) ||(((int)lastName.getText().charAt(j) > 90 )&&((int)lastName.getText().charAt(j)<97)) || ((int)(int)lastName.getText().charAt(j))>122 ){

                            wrongName.setText("Names  Can't have special Characters Or Numbers");

                        }

                        else{
                            wrongName.setText("");



                        }
                    }
                }
            }

        }

         //Username Validation
        if(username.getText().isBlank()) {
            userWrong.setText("User-Name is Required");
        }

        else{
            for(int i=0; i<(username.getText().length()); i++){

                if(((int)username.getText().charAt(i) < 65 && ((int)username.getText().charAt(i) < 48 || (int)username.getText().charAt(i) > 57 ) ) ||(((int)username.getText().charAt(i) > 90 )&&((int)username.getText().charAt(i)<97)) || ((int)(int)username.getText().charAt(i))>122 ){

                    userWrong.setText("Username Can't have special Characters");

                }

                else{
                    userWrong.setText("");
                }
        }



        }


        if(pass1.getText().isBlank()){
            wrongpass1.setText("Password is Required");

        }else{
            wrongpass1.setText("");

        }
         if(pass2.getText().isBlank()){
            wrongPass.setText("Confirm Password is Required");

        }else{
             wrongPass.setText("");

         }
         if(!((pass1.getText()).equals(pass2.getText()))&&(!pass1.getText().isBlank() ||!pass2.getText().isBlank() )){
                wrongPass.setText("The Passwords Doesn't Match");
            }
            else{
            wrongPass.setText("");

            }


          if(wrongpass1.getText().isBlank()&&wrongPass.getText().isBlank()&&userWrong.getText().isBlank()
          &&wrongName.getText().isBlank()
          ){
              SocketClient socketClient = SocketClient.getInstance();
              JSONObject json = new JSONObject();
              json.put("username", username.getText());
              json.put("password", pass1.getText());
              json.put("firstname", firstName.getText());
              json.put("lastname", lastName.getText());
              json.put("balance", "2000");
              json.put("type", "1");

              String dist = "Sign_up";
              JSONObject serverResponse = socketClient.socketSendReceiveJSON(json, dist);
              System.out.println(serverResponse.get("result"));
                if(serverResponse.get("result").equals("success")){

                    Stage primaryStage = (Stage)((Node)event.getSource()).getScene().getWindow();
                    //Market Screen
                    FXMLLoader LoginPaneLoader = new FXMLLoader(getClass().getResource("LogIn.fxml"));
                    Parent LoginPane = LoginPaneLoader.load();
                    Scene LoginScene = new Scene(LoginPane, 850, 500);
                    primaryStage.setScene(LoginScene);

                }else{

                    wrongPass.setText("Username already Exists.\nTry a different Username.");

                }


          }else{
              System.out.println("Sign up Not Validated");
          }




        }






}

