package com.pets.utils.ValidationUtils;

import io.qameta.allure.Allure;
import org.skyscreamer.jsonassert.Customization;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.skyscreamer.jsonassert.comparator.CustomComparator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class JsonResponseValidationUtils {

    public static void jsonResponseValidationSTRICT(String actualJson, String expectedResponse, Boolean isFilePath) throws IOException {
        String expectedJson = isFilePath ? new String(Files.readAllBytes(Paths.get(expectedResponse))): expectedResponse;
        Allure.step("Validation complete JSON response", () ->{
            JSONAssert.assertEquals("The complete JSON response did not match the expected",
                    expectedJson, actualJson, JSONCompareMode.STRICT);
        });
    }

    public static void jsonResponseValidationSTRICT(String actualJson, String expectedJson, List<String> attributesToIgnore) {
             Allure.step("Validation complete JSON response + ignore: " + attributesToIgnore, () ->{
                 Customization[] customizations = attributesToIgnore.stream()
                                 .map(attribute -> new Customization(attribute,(expected,actual)-> true))
                                 .toArray(Customization[]::new);

            JSONAssert.assertEquals("The complete JSON response did not match the expected & ignore: " +attributesToIgnore,
                    expectedJson, actualJson, new CustomComparator(JSONCompareMode.STRICT, customizations));
        });
    }
}
