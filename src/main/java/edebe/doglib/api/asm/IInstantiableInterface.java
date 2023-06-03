package edebe.doglib.api.asm;

import org.objectweb.asm.ClassWriter;

public interface IInstantiableInterface {
    void makeMethod(ClassWriter classWriter, String methodName, String className);
}