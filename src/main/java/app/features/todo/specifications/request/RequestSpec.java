package app.features.todo.specifications.request;

import app.common.configuration.Configuration;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import app.features.user.model.User;

import java.util.List;

public class RequestSpec {

    private static RequestSpecBuilder baseSpecBuilder() {
        return new RequestSpecBuilder()
                .setBaseUri(Configuration.getProperty("base.url"))
                .addFilters(List.of(
                        new RequestLoggingFilter(),
                        new ResponseLoggingFilter(),
                        new AllureRestAssured()))
                .setContentType(ContentType.JSON)
                .setAccept(ContentType.JSON);
    }

    public static RequestSpecification unauthSpec() {
        return baseSpecBuilder().build();
    }

    public static RequestSpecification authSpec(User user) {
        return baseSpecBuilder()
                .setAuth(io.restassured.RestAssured.preemptive().basic(user.getLogin(), user.getPassword()))
                .build();
    }

    public static RequestSpecification authSpecAsAdmin() {
        return authSpec(new User("admin", "admin"));
    }

}
