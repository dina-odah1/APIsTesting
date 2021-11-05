import java.util.*;
import java.io.*;
public class user {
    public static void main(String[] args)throws Exception{
        String FileName = "./src/main/resources/GlobalVariables.properties";
        FileReader reader=new FileReader(FileName);

        Properties p=new Properties();
        p.load(reader);

        System.out.println(p.getProperty("username"));
        System.out.println(p.getProperty("baseURI"));
    }
}

