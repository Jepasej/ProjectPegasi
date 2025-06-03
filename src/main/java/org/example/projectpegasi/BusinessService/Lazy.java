package org.example.projectpegasi.BusinessService;

/**
 *  A helper class that delays the creation of an object of type T until it is actually
 *  needed by the user. The object is created only once and then reused.
 *
 * @param <T> The type of object this class creates and returns - scenes in our case.
 */
public class Lazy<T>
{
    private T value = null;
    private ConstWrapper<T> wrapperFunction;

    /**
     * Constructs a new Lazy object with a given function that provides the value when needed.
     * @param wrapperFunction A wrapper that defines how the value should be instantiated lazily
     */
    public Lazy(ConstWrapper<T> wrapperFunction)
    {
        this.wrapperFunction = wrapperFunction;
    }

    /**
     * Returns the lazy initialized value. If the value has not been initialized yet,
     * it will be created using the provided wrapperFunction.
     * @return The initialized value of type T
     */
    public T getValue()
    {
        if(value == null)
        {
            value = wrapperFunction.getInstance();
        }

        return value;
    }
}
