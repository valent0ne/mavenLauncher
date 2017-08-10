import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Main {

    //logger
    final private static Logger LOGGER = LoggerFactory.getLogger(Main.class);

    //line separator
    final private static String LINE_SEPARATOR = System.lineSeparator();


    //CLI params declaration


    //path alla root del progetto
    @Parameter(names = {"--pom", "-p"}, required = true, description = "absolute path to project directory")
    private static String pathToPom;


    //elenco dei goals
    @Parameter(names = {"--goal", "-g"}, description = "maven goal")
    private static List<String> goals = new ArrayList<>(Arrays.asList("clean", "install"));


    public static void main(String[] args) {
        Main main = new Main();
        JCommander jc = JCommander.newBuilder()
                .addObject(main)
                .build();

        jc.setProgramName("java -jar mavenLauncher.jar");

        try{
            jc.parse(args);

        }catch (Exception e){

            jc.usage();
            System.exit(1);
        }


        if(! (pathToPom.substring(pathToPom.length()-8, pathToPom.length()).equals("/pom.xml"))){
            pathToPom = pathToPom.concat("/pom.xml");
        }


        main.run();

    }


    private void run(){

        separator();
        LOGGER.info("[STARTING PROCESS]{}", LINE_SEPARATOR);

        String args = "";

        for(String s : goals){
            args += (s+" ");
        }

        try{

            String command = "mvn -f "+pathToPom+" "+args;

            LOGGER.info("running: {}", command);

            Process proc = Runtime.getRuntime().exec(command);

            // Read the output

            BufferedReader reader = new BufferedReader(new InputStreamReader(proc.getInputStream()));

            String line = "";
            while((line = reader.readLine()) != null) {
                System.out.print(line + "\n");
            }

            proc.waitFor();


        }catch(Throwable e){
            LOGGER.error(e.getMessage());
            separator();
            LOGGER.error("[PROCESS ABORTED]{}", LINE_SEPARATOR);
            System.exit(1);
        }
        separator();
        LOGGER.info("[PROCESS COMPLETED]{}", LINE_SEPARATOR);
    }



    private void separator(){ System.out.println(" "); }

}
