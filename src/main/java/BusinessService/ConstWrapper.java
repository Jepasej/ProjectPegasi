package BusinessService;

/**
 * A generic interface that defines a method for building and returning an object of type T.
 * @param <T> The type of object this class creates and returns - scenes in our case.
 */
public interface ConstWrapper<T> {
    T getInstance();
}
