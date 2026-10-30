package com.pets.utils;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import io.qameta.allure.Allure;
import org.testng.ITestResult;
import java.io.FileOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Properties;
import java.util.Set;


public class AllureUtils {

    private  AllureUtils(){}
    public  static void addTestNgGroupsasAllureTags(ITestResult result){
        if(result == null || result.getMethod() == null) return;

        String[] groups = result.getMethod().getGroups();
        if (groups == null || groups.length == 0) return;

        Set<String> unique = new LinkedHashSet<>();
        Arrays.stream(groups)
                .filter(String g -> g != null && !g.isBlank())
                .map(String::trim)
                .forEach(unique::add);

        for(String g : unique){
            Allure.label("tag",g); // filterable in Allure as Tag
        }
    }

    public  static void write() {
        String resultsDir = System.getProperty(
                "allure.results.directory",
                "target/allure-results"
        );

        Path file = Paths.get(resultsDir,"environment.properties");
        Properties props = new Properties();


        put(props, "Test Environment", System.getProperty("env"));
    }
}
