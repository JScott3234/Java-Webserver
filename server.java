import java.io.*;
import java.net.*;

public class server{
    static final int port = 8080;
    public static void main(String[] args) throws IOException {
        try (ServerSocket s = new ServerSocket(port)) {
            System.out.println("Listening for connection on port " + port);
                while(true){
                    Socket cliSock = s.accept();
                    Runnable service = new clicon(cliSock);
                    Thread serv = new Thread(service);
                    serv.start();
                }
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}