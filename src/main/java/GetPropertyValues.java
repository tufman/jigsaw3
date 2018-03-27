import java.io.*;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.Properties;


public class GetPropertyValues {


    InputStream inputStream;

    //public HashMap <String, String> getPropValues() throws IOException {
    public Properties getPropValues() throws IOException {
        HashMap <String, String> retValConfiguration = new HashMap<>();
        Properties prop = null;
        try {
            prop = new Properties();
            String propFileName = "config.properties";

            URL resource = getClass().getClassLoader().getResource(propFileName);
            System.out.println(resource.getPath());
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }


//            try(FileInputStream fis = new FileInputStream(propFileName);
//            //InputStreamReader isr = new InputStreamReader(inputStream);
//            InputStreamReader isr = new InputStreamReader(fis);
//            BufferedReader br = new BufferedReader(isr)){
//            try(BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))){
//                String line;
//                while ((line = br.readLine()) != null) {
//                    String [] splittedLine = line.split("=");
//                    retValConfiguration.put(splittedLine[0].trim(), splittedLine[1]);
//                }
//            }
//
//
//
//            retValConfiguration.put("missingElementInConfigurationFile", prop.getProperty("missingElementInConfigurationFile"));
//            retValConfiguration.put("errorCode1", prop.getProperty("errorCode1"));

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        //return result;
        return prop;
    }


}
