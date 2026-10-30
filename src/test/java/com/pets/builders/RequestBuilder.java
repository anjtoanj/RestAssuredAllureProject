package com.pets.builders;

import com.pets.specs.RequestSpecFactory;
import io.restassured.specification.RequestSpecification;
import io.restassured.builder.RequestSpecBuilder;


public class RequestBuilder {

    private final RequestSpecification spec;

    public RequestBuilder(String apiBasePathKey) {
        this.spec = RequestSpecFactory.baseSpec(apiBasePathKey);
    }

    public RequestBuilder header(String key, String value) {
        spec.header(key, value);
        return this;
    }

    public RequestBuilder queryParam(String key, Object value) {
        spec.header(key, value);
        return this;
    }

    public RequestBuilder pathParam(String key, Object value) {
        spec.header(key, value);
        return this;
    }

    public RequestBuilder body( Object body) {
        spec.body(body);
        return this;
    }

    public RequestSpecification build() {
        return spec;
    }

}
