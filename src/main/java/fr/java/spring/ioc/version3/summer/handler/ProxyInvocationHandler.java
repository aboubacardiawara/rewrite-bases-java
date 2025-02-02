package fr.java.spring.ioc.version3.summer.handler;

import fr.java.spring.ioc.common.exception.SummerException;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.function.Supplier;

public class ProxyInvocationHandler implements InvocationHandler {

    private final Object objectToHandle;
    private final CacheableHandler cacheableHandler;

    public ProxyInvocationHandler(Object objectToHandle) {
        this.objectToHandle = objectToHandle;
        this.cacheableHandler = new CacheableHandler(objectToHandle);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) {
        boolean cacheableMethod = cacheableHandler.isSupported(method);
        Supplier<Object> resultSupplier = () -> invokeMethod(method, args);
        return cacheableMethod
                ? cacheableHandler.getFromCacheOrCompute(method, args, resultSupplier)
                : invokeMethod(method, args);
    }

    private Object invokeMethod(Method method, Object[] args) {
        try {
            return method.invoke(objectToHandle, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new SummerException("Error occurred in proxy", e);
        }
    }
}