import java.io.*;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Client {
    public static void main(String[] args) throws IOException {
        //Prompt the user to enter an address in the form file/text.txt for directory
        System.out.println("Enter the file path (ex. folderName/File.txt)");
        //Get the input from the keyboard
        BufferedReader std= new BufferedReader(new InputStreamReader(System.in));
        //A string where the directories will be checked
        String userInput;
        while((userInput=std.readLine()) !=null) //sets userInput equal to input from keyboard
        {
            //Create the request Http 1.0
            String request= "GET "+userInput+ " HTTP/1.0\r\nHost: www.private.com\r\nConnection: close User-agent: Personal/4.0\r\nAccept: text/html, image/gif,image/jpeg\r\nAccept-language:fr\r\n(extra carriage return + line feed)";

            //Client Socket gets created
            Socket socket = new Socket("localhost", 5000);

            //Connect client output to server input
            DataOutputStream out = new DataOutputStream(new BufferedOutputStream(socket.getOutputStream()));

            //Connect client input to server output
            DataInputStream in = new DataInputStream(new BufferedInputStream(socket.getInputStream()));

           //send request
            byte [] send=request.getBytes(); //sends the string in a byte array form, or use out.writeUTF(request);
            out.write(send);
            out.flush();

            //RECEIVE DATA
            //Since the first line is the response type we first have to get it to proceed later
            String response;
            response = in.readLine();//gets the first line
            int rSize=response.getBytes().length;
            System.out.println(response);//prints the response to the console

            //parse the response to get the code type
            String [] message =response.split(" ");

            int result = Integer.parseInt(message[1]);//parse to int to prevent spaces causing errors
            //System.out.println(result);
            if(result==200) { //execute only if the code is 200
                //System.out.println("Downloading File 200");
                String fileName=getFileName(userInput);//selects the name and type of the file from the user input
                FileOutputStream fos= new FileOutputStream("E:\\"+fileName);//writes to that directory
                byte[] contents= new byte[10020];//only creates a buffer if file is found
                in.read(contents,0,contents.length);
                fos.write(contents,0,contents.length);
                fos.close();
            }
            //String fileName=getFileName(userInput);//selects the name and type of the file from the user input
         socket.close();
        }


    }
    public static String getFileName(String s){
        String [] d=s.split("\\\\");
        String toReturn=d[d.length-1];
        return toReturn;
    }
}

/*  FileOutputStream fos= new FileOutputStream("E:\\contactReceived.html");//writes to that directory
            byte[] contents= new byte[10020];
            String response;
            response = in.readLine()+" \r\n";//gets the first line
            int rSize=response.getBytes().length;
            System.out.println(response+ "Size: "+rSize);//prints the response to the console
            in.read(contents,0,contents.length);
            fos.write(contents,0,contents.length);
            fos.close();
            socket.close();//closes connection*/


