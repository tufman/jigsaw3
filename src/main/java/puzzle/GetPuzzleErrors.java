package puzzle;

import java.io.*;
import java.net.URL;
import java.util.Properties;


public class GetPuzzleErrors {




    public Properties getPropValues() throws IOException {

        InputStream inputStream = null;

        Properties prop = null;
        try {
            prop = new Properties();
            String propFileName = "config.properties";

            URL resource = this.getClass().getClassLoader().getResource(propFileName);
            System.out.println(resource.getPath());
            inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
            if (inputStream != null) {
                prop.load(inputStream);
            } else {
                throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
            }

        } catch (Exception e) {
            System.out.println("Exception: " + e);
        } finally {
            inputStream.close();
        }
        return prop;
    }


}
