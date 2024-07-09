import java.net.*;
import java.util.*;
import java.io.*;

public class clicon implements Runnable{

    private Socket soc;
    

    public clicon(Socket soc){
        this.soc = soc;
    }

    
    public void run() {
        fileSend(soc);

    }

    /**
     *  A Client Request Reader - Serves as a Test for Recieving Clients
     * @param clientSocket - the connected Socket
     * @throws IOException - no common errors to be thrown yet
     */
    public void clientRequestView(Socket clientSocket) throws IOException{
        InputStreamReader isr = new InputStreamReader(clientSocket.getInputStream());
        BufferedReader reader = new BufferedReader(isr);
        String line = reader.readLine();
        while(line != null && !line.isEmpty()){
            System.out.println(line); // prints out the browser request
            line = reader.readLine();
        }
        isr.close();
        reader.close();
    }

    /**
     *  Basic Testing for Data Transmission, sends over the current date
     * @param soc - the socket
     */
    public void dateTest(Socket soc){
        Date today = new Date();
    String hTextResp = "HTTP/1.1 200 OK\r\n\r\n" + today;
    try {
        OutputStream out = soc.getOutputStream();
        out.write(hTextResp.getBytes("UTF-8"));
        
        out.flush();
        System.out.println("Sent!");
        out.close();
    } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
    } catch (IOException e) {
        e.printStackTrace();
    }
    }

    /**
     *  The Main File Transfer Method, 
     * @param soc
     */
    public void fileSend(Socket soc){

        BufferedReader in = null;
        OutputStream out = null;
        FileInputStream FIS = null;

        try{
            in = new BufferedReader(new InputStreamReader(soc.getInputStream()));
            out = soc.getOutputStream();

            String protocolReq = in.readLine();
            if(protocolReq == null){
                out.write("HTTP/1.1 400 Bad Request\r\n\r\n".getBytes());
                return;
            }

            String[] hyperTextParts = protocolReq.split(" ");
            if(hyperTextParts.length < 2){
                out.write("HTTP/1.1 400 Bad Request\r\n\r\n".getBytes());
                return;
            }

            String name = hyperTextParts[1];
            if(name.equals("/")){
                name = "/index.html";
            }

            File file = new File("." + name);

            try{
                FIS = new FileInputStream(file);

                out.write("HTTP/1.1 200 OK\r\n".getBytes());
                out.write("Content-type: text/html\r\n\r\n".getBytes());

                byte[] buff = new byte[1024];
                int bytesRead = 0;
                while((bytesRead = FIS.read(buff)) != -1){
                    out.write(buff, 0, bytesRead);
                }
            } catch (FileNotFoundException e){
                out.write("HTTP/1.1 404 Not Found\r\n\r\n".getBytes());
            }

            out.flush();
            

        } catch (IOException e){
            e.printStackTrace();
        } finally {
            try{
                if(FIS != null) FIS.close();
                if(out != null) out.close();
                if(in != null) in.close();
                if (soc != null && !soc.isClosed()) soc.close();
            } catch (IOException e){
                e.printStackTrace();
            }
            
        }
    }

    
    
}
