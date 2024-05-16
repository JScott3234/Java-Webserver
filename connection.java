import java.io.*;
import java.net.*;

// This is to be implemented later,
// once I've opened up Threads for the main,
// I'll work to apply the Client Connection Thread Nodes
// Also plan to add Javadoc Later lol

public class connection implements Runnable{

    private Socket socket;
    //private String message = "Connected!"; // DEBUG thing from when I first made it
    private String protocolReq;
    private String[] hTextParts;
    private String name;
    private byte[] buff;
    public FileInputStream fileIS; // Not sure what would happen to client browser if I made it private, so I'm leaving it public

    public connection(Socket socket){
        this.socket = socket;
    }

    public void run() {
        try{
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            OutputStream out = socket.getOutputStream();

            protocolReq = in.readLine();
            hTextParts = protocolReq.split(" ");
            name = hTextParts[1];

            if(name.equals("/")){
                name = "/index.html";
            }

            File file = new File("." + name);
            
            try{
                fileIS = new FileInputStream(file);

                out.write("HTTP/1.1 200 OK\r\n".getBytes());
                out.write("Content-type: text/html\r\n\r\n".getBytes());


                // gotta use bytes, was kinda hoping we could use strings like in the browser
                buff = new byte[1024];
                int i;
                while (( i = fileIS.read(buff)) != -1) {
                    out.write(buff, 0, i);
                }
                fileIS.close();

            } catch (FileNotFoundException e){
                out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            }

            out.close();
            in.close();
            socket.close();
            
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    
}
