package edebe.doglib.api.helper;

import net.minecraft.core.Direction;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

import javax.annotation.Nullable;

public interface CapabilityHelper {
    static <C> C getCapabilityOrNull(ICapabilityProvider provider, Capability<C> capability) {
        return getCapabilityOrElse(provider, capability, null);
    }

    static <C, T> T getCapabilityOrNull(ICapabilityProvider provider, Capability<C> capability, Class<T> clazz) {
        return getCapabilityOrElse(provider, capability, null, clazz);
    }

    @SuppressWarnings("ConstantConditions")
    static <C> C getCapabilityOrElse(ICapabilityProvider provider, Capability<C> capability, @Nullable C other) {
        return provider.getCapability(capability).orElse(other);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    static <C, T> T getCapabilityOrElse(ICapabilityProvider provider, Capability<C> capability, @Nullable C other, Class<T> clazz) {
        T type;
        try {
            type = (T) provider.getCapability(capability).orElse(other);
        } catch (ClassCastException e) {
            throw new ClassCastException(other == null ? "" : "Cannot cast " + other.getClass().getName() + " to " + clazz.getName());
        }
        return type;
    }

    @SuppressWarnings("ConstantConditions")
    static <C> C getCapabilityOrElse(ICapabilityProvider provider, Direction facing, Capability<C> capability, @Nullable C other) {
        return provider.getCapability(capability, facing).orElse(other);
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    static <C, T> T getCapabilityOrElse(ICapabilityProvider provider, Direction facing, Capability<C> capability, @Nullable C other, Class<T> clazz) {
        T type;
        try {
            type = (T) provider.getCapability(capability, facing).orElse(other);
        } catch (ClassCastException e) {
            throw new ClassCastException(other == null ? "" : "Cannot cast " + other.getClass().getName() + " to " + clazz.getName());
        }
        return type;
    }
}