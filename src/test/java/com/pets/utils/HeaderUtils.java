package com.pets.utils;

import io.restassured.response.Response;
import org.testng.asserts.SoftAssert;

import java.util.HashMap;
import java.util.Map;

import static com.pets.constants.HeaderConstants.*;


public class HeaderUtils {

    public static Map<String, String> getDefaultHeaders() {

        Map<String, String> headers = new HashMap<>();
        headers.put(INSTM, INSTNUM_DEFAULT);
        return headers;
    }

    public static Map<String, String> getStaticHeaders() {

        Map<String, String> headers = new HashMap<>();
        headers.put(INSTM, INSTNUM_DEFAULT);
        headers.put(FLAG4, FLAG4_DEFAULT);
        return headers;
    }

    public static Map<String, String> modifyHeaderValue(Map<String, String> defaultHeaders, String header, String value) {
        defaultHeaders.remove(header);
        defaultHeaders.put(header, value);
        return defaultHeaders;
    }

    public static Map<String, String> removeHeaderValue(Map<String, String> defaultHeaders, String header) {
        defaultHeaders.remove(header);
        return defaultHeaders;
    }

    public static Map<String, String> getMalformedHeaders(String scenario) {
        Map<String, String> defaultHeaders = getDefaultHeaders();

        return switch (scenario) {
            case "INSTNUM_MISSING" -> removeHeaderValue(defaultHeaders, INSTM);
            case "INSTNUM_INVALID" -> modifyHeaderValue(defaultHeaders, INSTM, "helloWorld");
            default -> throw new IllegalArgumentException("UnHandled Header Type" + scenario);
        };
    }

    public static void errorResponseValidation(Response response, SoftAssert softAssert) {
        softAssert.assertNotNull(response.getHeader(INSTM),
                "Header '" + INSTM + " ' is missing");
    }
}



