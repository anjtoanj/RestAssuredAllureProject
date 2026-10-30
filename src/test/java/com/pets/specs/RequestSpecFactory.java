package com.pets.specs;

import com.jayway.jsonpath.Configuration;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.config.EncoderConfig;
import io.restassured.config.RestAssuredConfig;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RequestSpecFactory {

    private RequestSpecFactory(){

    }
 //Its main purpose is to avoid repeating the same API request configuration in every test case.
 //ready-made API request template.
    public static RequestSpecification baseSpec(String apiBasePathKey){
        return new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setBaseUri(ConfigBuilder.getBaseUrl())
                .setBasePath(apiBasePathKey)
                //RequestLogginFilter and ResponseLoggingFilter to ensure all request and Resposnes are logged in console- Avoid log statements in every test case
                .addFilter(new RequestLoggingFilter())
                .addFilter(new ResponseLoggingFilter())
                .setContentType(ContentType.JSON)
                .addFilter(new AllureRestAssured())
                .addHeader("Content-Type","application/json")
                .build();

    }

    public static RequestSpecification baseSpecNoLogs(String apiBasePathKey){
        return new RequestSpecBuilder()
                .setConfig(RestAssuredConfig.config().encoderConfig(EncoderConfig.encoderConfig().appendDefaultContentCharsetToContentTypeIfUndefined(false)))
                .setBaseUri(ConfigBuilder.getBaseUrl())
                .setBasePath(apiBasePathKey)
                .addHeader("Content-Type","application/json")
                .build();
    }

}
