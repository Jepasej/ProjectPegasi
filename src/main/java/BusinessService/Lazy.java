package BusinessService;

/**
 *  A helper class that delays the creation of an object of type T
 *  until it is actually needed by the user. The object is created only once and then reused.
 * @param <T> The type of object this class creates and returns - scenes in our case.
 */
public class Lazy<T>
{
    private T value = null;
    private ConstWrapper<T> wrapperFunction;

    public Lazy(ConstWrapper<T> wrapperFunction)
    {
        this.wrapperFunction = wrapperFunction;
    }

    public T getValue()
    {
        if(value == null)
        {
            value = wrapperFunction.getInstance();
        }

        return value;

    }
}
