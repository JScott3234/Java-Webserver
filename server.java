import java.io.*;
import java.net.*;
import java.util.*;

public class server{
    public static void main(String[] args) throws Exception {
        try (ServerSocket server = new ServerSocket(8080)) {
            System.out.println("Listening for connection on port 8080");
            while(true){
                try(Socket cliSock = server.accept()){
                    //clientRequestView(cliSock);
                    Date today = new Date();
                    String hTextResp = "HTTP/1.1 200 OK\r\n\r\n" + today;
                    cliSock.getOutputStream().write(hTextResp.getBytes("UTF-8"));
                }
            }
        }
    }

    public static void clientRequestView(Socket clientSocket) throws IOException{
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        while(!line.isEmpty()){
            System.out.println(line); // prints out the browser request
            line = reader.readLine();
        }
    }
}