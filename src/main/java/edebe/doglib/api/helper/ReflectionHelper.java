package edebe.doglib.api.helper;

public interface ReflectionHelper {
    static boolean hasMethod(Class<?> clazz, String name, Class<?>... parameterTypes) {
        try {
            clazz.getMethod(name, parameterTypes);
            return true;
        } catch (NoSuchMethodException e) {
            return false;
        }
    }
}