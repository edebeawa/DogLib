package edebe.doglib.api.helper;

import edebe.doglib.DogLib;
import sun.misc.Unsafe;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.ProtectionDomain;

public class UnsafeHelper {
    public static Class<?> defineClass(String name, byte[] code, int off, int len, ClassLoader loader, ProtectionDomain protectionDomain) {
        Class<?> newClass;
        try {
            Field f = Unsafe.class.getDeclaredField("theUnsafe"), f1 = Unsafe.class.getDeclaredField("theUnsafe");
            f.setAccessible(true);
            f1.setAccessible(false);
            Unsafe unsafe = (Unsafe) f.get(null);
            int i;//override boolean byte offset. should result in 12 for java 17
            for (i = 0; unsafe.getBoolean(f, i) == unsafe.getBoolean(f1, i); i++);
            Field f2 = Unsafe.class.getDeclaredField("theInternalUnsafe");
            unsafe.putBoolean(f2, i, true);//write directly into override to bypass perms
            Object internalUnsafe = f2.get(null);
            Method defineClass = internalUnsafe.getClass().getDeclaredMethod("defineClass", String.class, byte[].class, int.class, int.class, ClassLoader.class, ProtectionDomain.class);
            unsafe.putBoolean(defineClass, i, true);
            newClass = (Class<?>) defineClass.invoke(internalUnsafe, name, code, off, len, loader, protectionDomain);
        } catch (NoSuchFieldException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            DogLib.LOGGER.fatal(e.getMessage());
            for (StackTraceElement traceElement : e.getStackTrace())
                DogLib.LOGGER.info("\tat " + traceElement);
            throw new RuntimeException(e);
        }
        return newClass;
    }
}
