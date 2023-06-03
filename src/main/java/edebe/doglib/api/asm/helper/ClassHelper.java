package edebe.doglib.api.asm.helper;

import edebe.doglib.api.asm.IInstantiableInterface;
import edebe.doglib.api.helper.UnsafeHelper;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Opcodes;

import java.lang.reflect.Method;

public interface ClassHelper extends Opcodes {
    static <C> Class<C> makeInterfaceInstance(Class<C> clazz, IInstantiableInterface instantiableInterface, Class<?>... implement) {
        String[] implementNames = new String[implement.length];
        for (int i = 0;i < implement.length;i++) implementNames[i] = implement[i].getName().replaceAll("\\.", "/");
        return makeInterfaceInstance(clazz, instantiableInterface, implementNames);
    }

    @SuppressWarnings("unchecked")
    static <C> Class<C> makeInterfaceInstance(Class<C> clazz, IInstantiableInterface instantiableInterface, String... implementNames) {
        String className = clazz.getSimpleName() + "$implement";
        ClassWriter writer = new ClassWriter(ClassWriter.COMPUTE_MAXS);
        writer.visit(V1_5, ACC_PUBLIC + ACC_SUPER, className, null, "java/lang/Object", implementNames);
        MethodVisitor visitor = writer.visitMethod(ACC_PUBLIC, "<init>", "()V", null, null);
        visitor.visitVarInsn(ALOAD, 0);
        visitor.visitMethodInsn(INVOKESPECIAL, "java/lang/Object", "<init>", "()V");
        visitor.visitInsn(RETURN);
        visitor.visitMaxs(1, 1);
        visitor.visitEnd();
        Method[] methods = clazz.getMethods();
        for (Method method : methods) instantiableInterface.makeMethod(writer, method.getName(), className);
        writer.visitEnd();
        byte[] code = writer.toByteArray();
        return (Class<C>) UnsafeHelper.defineClass(className, code, 0, code.length, clazz.getClassLoader(), clazz.getProtectionDomain());
    }
}