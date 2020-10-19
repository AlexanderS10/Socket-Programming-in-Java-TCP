import java.io.File;

public class httpProtocol {
    String type,file,version,httpRequest;
    public httpProtocol(String a){
        httpRequest=a;
        parse();
    }
    public void parse(){
        String[] request=httpRequest.split("\\r?\\n");//This will take only the first line and ignore the rest
        String [] s= request[0].split(" ");//split the line into the different parts
        type = s[0];
        file = s[1];
        version = s[2].substring(0, 8);
    /*  System.out.println(type);
        System.out.println(file);
        System.out.println(version);*/
    }
    public int validate(){
        String directory= "E:\\"+file;//creates the file directory in the server
        File tmpDir = new File(directory);//this will check if the file exists
        boolean exists = tmpDir.exists();
        if(type.compareTo("GET")!=0 || type.isEmpty()||file.isEmpty()||version.isEmpty() || version.compareTo("HTTP/1.0")!=0){
            System.out.println("Invalid format");
            return 400;//"HTTP/1.0 400 Bad Request";
        }
        else if(!exists){
            return 404;//if the file was not found
        }
        else {
            //System.out.println(type + "\n" + file + "\n" + version);
            return 200;//if the file was found
        }
    }
    public String getDirectory(){
        return file;
    }
}
