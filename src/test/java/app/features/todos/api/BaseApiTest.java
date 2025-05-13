package app.features.todos.api;

import app.common.annotations.BeforeEachExtension;
import app.common.configuration.Configuration;
import app.common.dataGenerators.TestDataStorage;
import io.qameta.allure.junit5.AllureJunit5;
import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import app.features.todo.request.TodosRequest;
import app.features.todo.requester.TodosRequester;
import app.features.todo.specifications.request.RequestSpec;

@Slf4j
@ExtendWith(BeforeEachExtension.class)
@ExtendWith(AllureJunit5.class)
public class BaseApiTest {

    protected TodosRequester todosRequester;
    protected SoftAssertions softly;

    @BeforeAll
    public static void setup() {
        RestAssured.defaultParser = Parser.JSON;
        RestAssured.baseURI = Configuration.getProperty("base.url");

        try {
            RestAssured.port = Integer.parseInt(Configuration.getProperty("port").trim());
        } catch (NumberFormatException | NullPointerException e) {
            throw new RuntimeException("Invalid or missing 'port' in config.properties", e);
        }
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