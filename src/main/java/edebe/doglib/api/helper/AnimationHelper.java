package edebe.doglib.api.helper;

import net.minecraft.resources.ResourceLocation;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.model.AnimatedGeoModel;

public interface AnimationHelper {
    static <T extends IAnimatable> AnimatedGeoModel<T> getAnimatedGeoModel(ResourceLocation model, ResourceLocation texture, ResourceLocation animation) {
        return new AnimatedGeoModel<>() {
            @Override
            public ResourceLocation getModelResource(T object) {
                return model;
            }

            @Override
            public ResourceLocation getTextureResource(T object) {
                return texture;
            }

            @Override
            public ResourceLocation getAnimationResource(T animatable) {
                return animation;
            }
        };
    }

    static <T extends IAnimatable> AnimatedGeoModel<T> getAnimatedGeoModel(ResourceLocation location) {
        return getAnimatedGeoModel(
            new ResourceLocation(location.getNamespace(), "geo/" + location.getPath() + ".geo.json"),
            new ResourceLocation(location.getNamespace(), "textures/" + location.getPath() + ".png"),
            new ResourceLocation(location.getNamespace(), "animations/" + location.getPath() + ".animation.json")
        );
    }
}