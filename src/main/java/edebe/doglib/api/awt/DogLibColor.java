package edebe.doglib.api.awt;

import net.minecraft.util.Mth;
import net.minecraft.world.phys.Vec3;

import java.awt.*;
import java.awt.color.ColorSpace;

public class DogLibColor extends Color {
    public DogLibColor(int r, int g, int b) {
        super(r, g, b);
    }

    public DogLibColor(int r, int g, int b, int a) {
        super(r, g, b, a);
    }

    public DogLibColor(float r, float g, float b) {
        super(r, g, b);
    }

    public DogLibColor(float r, float g, float b, float a) {
        super(r, g, b, a);
    }

    public DogLibColor(int[] rgb) {
        super(rgb[0], rgb[1], rgb[2]);
    }

    public DogLibColor(float[] rgb) {
        super(rgb[0], rgb[1], rgb[2]);
    }

    public DogLibColor(int[] rgba, boolean hasAlpha) {
        super(rgba[0], rgba[1], rgba[2], hasAlpha ? rgba[3] : 255);
    }

    public DogLibColor(float[] rgba, boolean hasAlpha) {
        super(rgba[0], rgba[1], rgba[2], hasAlpha ? rgba[3] : 1);
    }

    public DogLibColor(int rgb) {
        super(rgb);
    }

    public DogLibColor(int rgba, boolean hasAlpha) {
        super(rgba, hasAlpha);
    }

    public DogLibColor(Color color) {
        super(color.getRGB());
    }

    public DogLibColor(Color color, boolean hasAlpha) {
        super(color.getRGB(), hasAlpha);
    }

    public DogLibColor(Color color, int alpha) {
        super(color.getRGB() | (alpha << 24), true);
    }

    public DogLibColor(Color color, float alpha) {
        super(color.getRGB() | ((int) (alpha * 255F) << 24), true);
    }

    public DogLibColor(ColorSpace space, float[] components, float alpha) {
        super(space, components, alpha);
    }

    public static DogLibColor getHSBColor(int h, int s, int b) {
        return new DogLibColor(HSBtoRGB(h % 360 / 360F, Mth.clamp(s, 0, 100) / 100F, Mth.clamp(b, 0, 100) / 100F));
    }

    public static DogLibColor getHSBColor(float h, float s, float b) {
        return new DogLibColor(HSBtoRGB(h, s, b));
    }

    public float getFloatRed() {
        return this.getRed() / 255F;
    }

    public float getFloatGreen() {
        return this.getGreen() / 255F;
    }

    public float getFloatBlue() {
        return this.getBlue() / 255F;
    }

    public float getFloatAlpha() {
        return this.getAlpha() / 255F;
    }

    public Vec3 getVec3RGB() {
        return new Vec3(this.getFloatRed(), this.getFloatGreen(), this.getFloatBlue());
    }
}
