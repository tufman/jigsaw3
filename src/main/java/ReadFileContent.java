import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class ReadFileContent {
    private String filePath = "C:\\Users\\st198j\\Desktop\\JavaStuff\\jigsaw\\src\\main\\resources\\inputFile";
    private int expectedNumOfElementsFromFirstLine;
    private List<Element> jigsawElementList = new ArrayList<>();
    private List<String> errorsReadingInputFile = new ArrayList<>();
    Properties prop = null;

    public ReadFileContent(){
        this.filePath = filePath;
    }

    public ReadFileContent(String filePath){
        this.filePath = filePath;

    }

    public void readInputFile() throws IOException {
        try(FileInputStream fis = new FileInputStream(filePath);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader br = new BufferedReader(isr)){

            initConfiguration();

            readDataFromFile(br);

        }
    }

    private void readDataFromFile(BufferedReader br) throws IOException {
        String line;
        while ((line = br.readLine()) != null){
            if (line.charAt(0) == '#'){
                continue;
            }
            if(line.contains("NumElements")){
                String [] numElementArr = line.split("=");
                //String tempValue = numElementArr[1].trim();
                expectedNumOfElementsFromFirstLine = Integer.parseInt(numElementArr[1].trim());
                continue;
            }
            String [] stringsFromLineArr = line.split(" ");
            int [] numFromLine = new int[stringsFromLineArr.length];
            int startLocation = 0;
            for (String str : stringsFromLineArr){
                try {
                    numFromLine[startLocation] = Integer.parseInt(str);
                    startLocation++;
                }catch (NumberFormatException e ) {
                    errorsReadingInputFile.add("odedtest");
                    continue;
                }

            }
            Element element = new Element(numFromLine[0], numFromLine[1], numFromLine[2],numFromLine[3],numFromLine[4]);
            jigsawElementList.add(element);
        }

        if (expectedNumOfElementsFromFirstLine != jigsawElementList.size()){
            errorsReadingInputFile.add(prop.getProperty("missingElementInConfigurationFile"));
            //TODO should we stop or throw exception?
        }

        //TODO check if a valid result is available

        //TODO in case (valid result) send jigsawElementList to Find solution

        //public JigsawSolver(List<Element> jigsawElementList, int [] numOfAvailableLineForSolution, Map<String, List<Integer>> cornersMap)
        Map<String, List<Integer>> cornersMap = new HashMap<>();
        int [] numOfAvailableLineForSolution = null;
        JigsawSolver jigsawSolver = new JigsawSolver(jigsawElementList, numOfAvailableLineForSolution,cornersMap);
    }


        public void printListOfElements(){
            for (Element element: jigsawElementList){
                System.out.println(element);
            }


        for (String errorMsg : errorsReadingInputFile){
            System.out.println(errorMsg);
        }

    }

    private void initConfiguration() throws IOException {
        GetPropertyValues properties = new GetPropertyValues();
        //configuration = properties.getPropValues();
        prop = properties.getPropValues();

//        for (Map.Entry<String, String> map : configuration.entrySet()){
//            System.out.print("Parameter: " + map.getKey() + " Value: " + map.getValue() + "\n");
//        }
        System.out.println("####################################");
        System.out.println("Existing Errors in config.properties");
        System.out.println("####################################");
        prop.forEach((key, value) -> System.out.println(key + " : " + value));
    }

    public int getNumOfElementsFromFirstLine(){
        return expectedNumOfElementsFromFirstLine;
    }


    public boolean verifyErrorExistInList(String error){
        return errorsReadingInputFile.contains(error);
    }

    public Element getFirstElement() {
        return jigsawElementList.get(0);
    }

    public int getActualNumOfElementsReadFromInputFile(){
        return jigsawElementList.size();
    }

}
