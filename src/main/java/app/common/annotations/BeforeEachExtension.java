package app.common.annotations;

import lombok.extern.slf4j.Slf4j;
import app.features.todo.model.Todo;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import app.features.todo.request.ValidatedTodosRequest;
import app.features.todo.specifications.request.RequestSpec;

import java.lang.reflect.Method;
import java.util.Optional;
import java.util.stream.IntStream;

import static app.common.dataGenerators.TestDataGenerator.generateTestData;

@Slf4j
public class BeforeEachExtension implements BeforeEachCallback {

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        Method testMethod = extensionContext.getRequiredTestMethod();

        Optional.ofNullable(testMethod.getAnnotation(PrepareTodo.class))
                .ifPresent(prepareTodo -> {
                    log.info("Creating {} Todo items", prepareTodo.value());
                    IntStream.range(0, prepareTodo.value())
                            .forEach(i -> {
                                Todo testTodo = generateTestData(Todo.class);
                                log.info("Generated Todo: {}", testTodo);
                                new ValidatedTodosRequest(RequestSpec.authSpecAsAdmin()).create(testTodo);
                            });
                });
    }

}