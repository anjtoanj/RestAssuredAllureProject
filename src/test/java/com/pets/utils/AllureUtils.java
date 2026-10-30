package com.pets.utils;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;


public class AllureUtils {

    private  AllureUtils(){}
    public  static void addTestNgGroupsasAllureTags(ITestResult result){
    //PURPOSE OF THIS METHOD : Take the TestNG group name and passed it to Allure as a tag.

        if(result == null || result.getMethod() == null) return;

        String[] groups = result.getMethod().getGroups();
        if (groups == null || groups.length == 0) return;

        Set<String> unique = new LinkedHashSet<>();
        Arrays.stream(groups)
                .filter(g -> g != null && !g.isBlank())
                .map(String::trim)
                .forEach(unique::add);

        for(String g : unique){
            Allure.label("tag",g); // filterable in Allure as Tag
        }
    }

    public  static void write() {
        //In Allure report, you want to know where the tests were executed.
        //You also want to know which groups were executed and how many threads were used.
        //"Where should I save my Allure results?"
        String resultsDir = System.getProperty(
                "allure.results.directory",
                "target/allure-results"
        );
        //Give the file a name
        // Now Java knows the complete file location: target/allure-results/environment.properties
        Path file = Paths.get(resultsDir,"environment.properties");

        //Prepare a place to store information
        Properties props = new Properties();

        //We want to write information into it.
        //Runtime execution parameters
        put(props, "Test Environment", System.getProperty("env"));
        put(props, "Groups", System.getProperty("groups"));
        put(props, "Excluded", System.getProperty("excludedGroups"));
        put(props, "Parallel Mode", System.getProperty("parallel"));
        put(props, "Thread Count", System.getProperty("threadCount"));

        try{
            Files.createDirectories(file.getParent());
            try(FileOutputStream fos = new FileOutputStream(file.toFile())){
                props.store(fos,"Execution Environment");

//                The resulting file will look something like this:
//                Test Environment=QA
//                Groups=smoke
//                Parallel Mode=methods
//                Thread Count=4
            }

        } catch (IOException e) {
            throw new RuntimeException("Failed to write Allure environment file", e);
        }
    }

    private static void put(Properties props, String key, String  value){
        if(value !=null && !value.isBlank()){
            props.setProperty(key, value);
        }
    }
}
