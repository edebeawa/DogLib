package edebe.doglib.mixin.geckolib;

import com.mojang.math.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Matrix4f.class)
public interface Matrix4fAccessor {
    @Accessor("m03")
    void setM03(float m03);

    @Accessor("m13")
    void setM13(float m13);

    @Accessor("m23")
    void setM23(float m23);
}