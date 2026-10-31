package com.pets.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.ResponseSpecification;


public class ResponseSpecFactory {

    private ResponseSpecFactory(){}

    /* checks for successful JSON responses */
    public static ResponseSpecification jsonSuccess() {
            return new ResponseSpecBuilder()
                    .expectContentType(ContentType.JSON)
                    .build();
    }
    /* checks for 200 Created JSON*/
    public static ResponseSpecification okJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
    /* checks for 201 Created JSON*/
    public static ResponseSpecification createdJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode (201)
                .expectContentType(ContentType.JSON)
                .build();
    }
    /* checks for 400 Created JSON*/
    public static ResponseSpecification badRequest() {
        return new ResponseSpecBuilder()
                .expectStatusCode (400)
                .expectContentType(ContentType.JSON)
                .build();
    }

    /* checks for "any error" JSON payload (many APIs still return JSON on 4XX/5XX) */
    public static ResponseSpecification errorJson() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .expectContentType(ContentType.JSON)
                .build();
    }
}
