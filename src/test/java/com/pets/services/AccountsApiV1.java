package com.pets.services;

import com.pets.builders.RequestBuilder;
import com.pets.core.APiKeys;
import com.pets.core.EndPoints;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.eclipse.jetty.io.EndPoint;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AccountsApiV1 {

    public Response getAccounts(String customerNumber, Map<String,String> headers){
        return given()
                .headers(headers)
                .spec(new RequestBuilder(APiKeys.ACCOUNTS_V1).build())
                .pathParam("customerNumber",customerNumber)
                .when()
                .get(EndPoints.GET_ACCOUNTS);

    }

    public Response getAccounts(String customerNumber){
        return given()
                .headers()
                .spec(new RequestBuilder(APiKeys.ACCOUNTS_V1).build())
                .pathParam("customerNumber",customerNumber)
                .when()
                .get(EndPoints.GET_ACCOUNTS);

    }
}
