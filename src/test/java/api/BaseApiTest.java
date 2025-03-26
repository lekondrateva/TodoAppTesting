package api;

import configuration.Configuration;
import dataGenerators.TestDataStorage;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import requests.TodosRequest;
import requests.TodosRequester;
import specifications.request.RequestSpec;

public class BaseApiTest {
    protected TodosRequester todosRequester;
    protected SoftAssertions softly;

    @BeforeAll
    public static void setup() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = Configuration.getProperty("base.url");
        RestAssured.port = 4242;
    }

    @BeforeEach
    public void setupTest() {
        todosRequester = new TodosRequester(RequestSpec.authSpecAsAdmin());
        softly = new SoftAssertions();
    }

    @AfterEach
    public void clean() {
        TestDataStorage.getInstance().getStorage()
                .forEach((k, v) ->
                        new TodosRequest(RequestSpec.authSpecAsAdmin())
                                .delete(k));

        TestDataStorage.getInstance().clean();
    }

    @AfterEach
    public void assertAll() {
        softly.assertAll();
    }

}