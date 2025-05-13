package app.common.dataGenerators;

import lombok.Getter;
import app.features.todo.model.Todo;

import java.util.HashMap;

@Getter
public class TestDataStorage {

    private static TestDataStorage instance;
    private HashMap<Long, Todo> storage;

    private TestDataStorage() {
        storage = new HashMap<>();
    }

    public static TestDataStorage getInstance() {
        if (instance == null) {
            instance = new TestDataStorage();
        }
        return instance;
    }

    public void addData(Todo todo) {
        storage.put(todo.getId(), todo);
    }

    public void clean() {
        storage = new HashMap<>();
    }

}