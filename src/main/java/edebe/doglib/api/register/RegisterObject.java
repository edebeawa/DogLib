package edebe.doglib.api.register;

import net.minecraft.resources.ResourceLocation;

public class RegisterObject<T> {
    private final ResourceLocation resourceLocation;
    private final T object;

    public RegisterObject(ResourceLocation resourceLocation, T object) {
        this.resourceLocation = resourceLocation;
        this.object = object;
    }

    public ResourceLocation getResourceLocation() {
        return this.resourceLocation;
    }

    public T getObject() {
        return this.object;
    }

    @SuppressWarnings("unchecked")
    public <O> O getObject(Class<O> clazz) {
        O object;
        try {
            object = (O) this.object;
        } catch (ClassCastException e) {
            throw new ClassCastException("Cannot cast " + this.object.getClass().getName() + " to " + clazz.getName());
        }
        return object;
    }
}