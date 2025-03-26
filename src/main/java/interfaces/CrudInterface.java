package interfaces;

public interface CrudInterface<T> {

    Object create(T item);

    Object update(long id, T item);

    Object readAll();

    Object readAll(int offset, int limit);

    Object delete(long id);

}