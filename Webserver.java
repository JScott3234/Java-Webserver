import java.io.*;
import java.net.*;

// Old Webserver Thing I made for a College Class,
// Applies Thread Objects to easily tend to cilents
// and to avoid errors and bugs
// I don't plan to have this used. Only here as an Example.

    public class Webserver {
    public static void main(String[] args) {
	System.out.println("Search \"http://localhost:8080\" in a browser.");
        try (ServerSocket ss = new ServerSocket(8080)) {
            while (true) {
                Socket clientSocket = ss.accept();
                Runnable conHandler = new connection(clientSocket);
                Thread conn = new Thread(new Thread(conHandler));
                conn.start();
            }
            } catch (IOException e) {
            e.printStackTrace();
            }
    }
}
