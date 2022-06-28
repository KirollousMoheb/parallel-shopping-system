package Shopping;

import Shopping.Controllers.CartController;
import Shopping.Controllers.LoginController;
import Shopping.Controllers.MarketController;
import Shopping.Controllers.SignUpController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application {

    public static final String CURRENCY = "$";
    @Override
    public void start(Stage primaryStage) throws Exception{
        primaryStage.setTitle("Mobile Market");

        //Login Screen
        FXMLLoader LoginPaneLoader = new FXMLLoader(getClass().getResource("Controllers/LogIn.fxml"));
        Parent LoginPane = LoginPaneLoader.load();
        Scene LoginScene = new Scene(LoginPane, 850, 500);

        FXMLLoader SignupPaneLoader = new FXMLLoader(getClass().getResource("Controllers/SignUp.fxml"));
        Parent SignupPane = SignupPaneLoader.load();
        Scene SignupScene = new Scene(SignupPane, 850, 500);

        // injecting second scene into the controller of the first scene


        // injecting first scene into the controller of the second scene
        SignUpController SiggnupPaneController = (SignUpController) SignupPaneLoader.getController();
        SiggnupPaneController.setLoginScene(LoginScene);


        LoginController LoginPaneController = (LoginController) LoginPaneLoader.getController();
        LoginPaneController.setSignupScene(SignupScene);




        primaryStage.setScene(SignupScene);
        primaryStage.show();
        SocketClient.initConnection(1234);

    }
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void stop(){
        // close socket connection
        System.out.println("Closing connection");
        SocketClient socketClient = SocketClient.getInstance();
        socketClient.closeConnection();

    }

}
