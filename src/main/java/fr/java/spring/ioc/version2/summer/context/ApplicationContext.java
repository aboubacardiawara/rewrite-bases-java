package fr.java.spring.ioc.version2.summer.context;

import fr.java.spring.ioc.common.annotation.Component;
import fr.java.spring.ioc.common.exception.SummerException;
import org.reflections.Reflections;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@SuppressWarnings("unchecked")
public class ApplicationContext {

    private final Set<Class<?>> componentBeans;
    private Map<Class<?>, Object> singletons;

    public ApplicationContext(Class<?> applicationClass) {
        final Reflections reflections = new Reflections(applicationClass.getPackage().getName());
        this.componentBeans = reflections.getTypesAnnotatedWith(Component.class);
        singletons = new HashMap<>();
    }

    public <T> T getBean(Class<T> clazz) {
        if (singletons.containsKey(clazz)) {
            return (T) singletons.get(clazz);
        }
        return getBeanNotCreatedYet(clazz);
    }

    public <T> T getBeanNotCreatedYet(Class<T> clazz) {
        final Class<T> implementation = getImplementation(clazz);
        T bean = createBean(implementation);
        singletons.put(clazz, bean);
        return bean;
    }

    private <T> Class<T> getImplementation(Class<T> item) {

        final Set<Class<?>> classes = componentBeans.stream().filter(element -> element == item)
                .collect(Collectors.toSet());

        if (classes.size() > 1) {
            throw new SummerException("There are more than 1 implementation for " + item.getName());
        }

        return (Class<T>) classes.stream()
                .findFirst()
                .orElseThrow(() -> new SummerException("No valid candidate for bean " + item));
    }

    private <T> T createBean(Class<T> implementation) {
        try {
            final Constructor<T> constructor = getConstructor(implementation);
            final Object[] parameters = getConstructorParameters(constructor);

            return constructor.newInstance(parameters);
        } catch (Exception e) {
            throw new SummerException("Exception occurred creating bean " + implementation.getName(), e);
        }
    }

    private <T> Constructor<T> getConstructor(Class<T> clazz) {
        final Constructor<T>[] constructors = (Constructor<T>[]) clazz.getConstructors();
        if (constructors.length == 1) {
            return constructors[0];
        }

        final Set<Constructor<T>> constructorsWithAnnotation = Stream.of(constructors)
                .filter(constructor -> constructor.getAnnotations().length != 0)
                .collect(Collectors.toSet());

        if (constructorsWithAnnotation.size() > 1) {
            throw new SummerException("More than 1 constructor for " + clazz.getName());
        }

        return constructorsWithAnnotation.stream()
                .findFirst()
                .orElseThrow(() -> new SummerException("Cannot find constructor for " + clazz.getName()));
    }

    private <T> Object[] getConstructorParameters(Constructor<T> constructor) {
        final Class<?>[] parameterTypes = constructor.getParameterTypes();
        int parametersCount = parameterTypes.length;
        Object[] parameterObjects = new Object[parametersCount];
        for (int i = 0; i < parameterTypes.length; i++) {
            Class<?> parameterType = parameterTypes[i];
            parameterObjects[i] = createBean(candidatClass(parameterType));
        }
        return parameterObjects;
    }

    private Class<?> candidatClass(Class<?> typeDefinition) {
        if (typeDefinition.isInterface()) {
            Set<Class<?>> candidats = (Set<Class<?>>) getSubTypesOfInterface(typeDefinition);
            if (candidats.size() == 1) {
                return candidats.stream().findFirst().get();
            }
            Set<Class<?>> candidatsWithAnnotation = candidats.stream()
                    .filter(candidat -> candidat.getAnnotations().length != 0)
                    .collect(Collectors.toSet());

            if (candidatsWithAnnotation.size() > 1) {
                throw new SummerException("There are more than 1 implementation for " + typeDefinition.getName());
            }

            return candidatsWithAnnotation.stream()
                    .findFirst()
                    .orElseThrow(() -> new SummerException("Any implementation for " + typeDefinition));
        }
        return typeDefinition;
    }

    private Set<?> getSubTypesOfInterface(Class<?> interfaceDefiniton) {
        Reflections reflections = new Reflections(interfaceDefiniton.getPackage().getName());
        return reflections.getSubTypesOf(interfaceDefiniton);
    }
}
