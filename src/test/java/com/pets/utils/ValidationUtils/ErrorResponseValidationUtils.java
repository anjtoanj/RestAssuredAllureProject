package com.pets.utils.ValidationUtils;

import io.restassured.response.Response;
import org.json.JSONException;
import org.json.JSONObject;
import org.testng.asserts.SoftAssert;

public class ErrorResponseValidationUtils {

    private static void validateCode(Integer expectedCode, JSONObject responseObject, SoftAssert softAssert) throws JSONException {
        Integer actualCode = responseObject.getInt("code");
        softAssert.assertEquals(actualCode, expectedCode, "Code did not match");
    }

    private static void validateMessage(String expectedMessage, JSONObject responseObject, SoftAssert softAssert) throws JSONException {
        String actualMessage = responseObject.getString("message");
        softAssert.assertTrue(actualMessage.contains(expectedMessage), "Message did not match");
    }

    private static void validateStatus(Integer status, Response response, SoftAssert softAssert) throws JSONException {
        Integer actualStatus = response.getStatusCode();
        softAssert.assertEquals(actualStatus, status, "Status did not match");
    }

    private static void errorResponseValidation(Response response, String status, String code, String message, SoftAssert softAssert) throws JSONException {
        response.then().assertThat().statusCode(Integer.parseInt(status));

        JSONObject responseObject = new JSONObject(response.getBody().asString());

        validateCode(Integer.parseInt(code), responseObject, softAssert);
        validateMessage(message, responseObject, softAssert);
        validateStatus(Integer.parseInt(status), response, softAssert);
    }
}
