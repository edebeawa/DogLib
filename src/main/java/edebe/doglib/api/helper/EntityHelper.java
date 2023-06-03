package edebe.doglib.api.helper;

import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

public interface EntityHelper {
    static Vec3 getThrowableProjectileDeltaMovement(Entity entity) {
        float f = 0.4F;
        float rotationYaw = (-entity.getYRot()) % 360.0F;
        float rotationPitch = (entity.getXRot() + 180) % 360.0F;

        double mx = -(Mth.sin(rotationYaw / 180.0F * (float) Math.PI) * Mth.cos(rotationPitch / 180.0F * (float) Math.PI) * f / 2D);
        double mz = -(Mth.cos(rotationYaw / 180.0F * (float) Math.PI) * Mth.cos(rotationPitch / 180.0F * (float) Math.PI) * f) / 2D;
        double my = Mth.sin(rotationPitch / 180.0F * (float) Math.PI) * f / 2D;
        return new Vec3(mx, my, mz);
    }
}
