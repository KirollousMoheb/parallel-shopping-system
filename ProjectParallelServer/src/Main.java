import java.net.*;
import java.io.*;

public class Main {
    public static void main(String[] args) {
        SocketServer socketServer = SocketServer.startServer(1234);
        int clientNo = 0;
        AdminData user=new AdminData();
        user.getAdminData();
        while(true){
            Socket socket = null;

            try {
                System.out.println("Waiting for a client ...");
                socket = socketServer.getServer().accept();
            } catch (IOException e) {
                e.printStackTrace();
            }

            clientNo ++;
            System.out.println("Client connected");
            new ServerThread(socket, clientNo).start();
        }
    }
}