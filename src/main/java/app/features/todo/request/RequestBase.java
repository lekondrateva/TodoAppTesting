package app.features.todo.request;

import io.restassured.specification.RequestSpecification;

public abstract class RequestBase {

    protected RequestSpecification reqSpec;

    public RequestBase(RequestSpecification reqSpec) {
        this.reqSpec = reqSpec;
    }

}