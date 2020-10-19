import java.net.*;
import java.io.*;
import java.util.Arrays;

public class ServerThread extends Thread {
    private Socket socket = null;

    public ServerThread(Socket socket) {
        super("KKMultiServerThread");
        this.socket = socket;
    }
    public void run() {
        String directory="E:\\";
        //File file = new File(directory);

        try  {
            //Connect server output to client input
            DataOutputStream out= new DataOutputStream(socket.getOutputStream());

            //Connect server input to client output
            DataInputStream in=new DataInputStream(socket.getInputStream());

            //Receives the request from the client
            String request=new String(in.readLine());//reads the first line as needed
            System.out.println("Client Request: \n"+request);//prints out to the console

            //Parse the request to verify if directory exists or has any errors
            httpProtocol protocol=new httpProtocol(request);
            int validate=protocol.validate();
            if (validate==400) {
                String r="HTTP/1.0 400 Bad Request\r\n";
                byte [] response=r.getBytes();
                out.write(response);//sends the bad request response
                out.flush();
            }

            else if(validate==200){//file is found
                String r="HTTP/1.0 200 OK \r\n";//response message
                byte [] response=r.getBytes();
                int l=r.length();
                byte[] content= Arrays.copyOf(response,10000+l);//creates a buffer size with the header
                File file=new File(directory+protocol.getDirectory());//creates a new file with the directory sent by the client
                FileInputStream fis=new FileInputStream(file);//put the file in a file stream
                fis.read(content,l,(int) file.length());
                out.write(content,0,content.length);
                //System.out.print(new String(content));
                fis.close();
                out.flush();
            }
            else{//file not found
                String r="HTTP/1.0 404 Not Found\r\n";
                byte [] response=r.getBytes();
                out.write(response);//sends the response of not found
                out.flush();
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}


/*System.out.println("Sending file");
            String r="HTTP/1.0 200 OK \r\n";
            byte [] response=r.getBytes();
            System.out.print(new String(response));
            int l=r.length();
            byte[] content= Arrays.copyOf(response,10000+l);//creates a file size
            System.out.print(new String(content));
            System.out.print(new String(content));
            //out.write(response,0,content[l]-1);//sends the ok response

            //out.flush();
            File file=new File("E:\\something\\contact.html");
            FileInputStream fis=new FileInputStream(file);//put the file in a file stream
            fis.read(content,l,(int) file.length()+l);
            out.write(content,0,content.length);
            System.out.print(new String(content));
            fis.close();
            out.flush();*/
