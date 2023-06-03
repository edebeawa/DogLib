package edebe.doglib.api.helper;

import net.minecraft.util.Mth;

import java.text.NumberFormat;

public class MathHelper {
    public static int[] hsvToRgb(float h, float s, float v) {
        int i = (int)(h * 6F) % 6;
        float f = h * 6F - (float)i;
        float f1 = v * (1F - s);
        float f2 = v * (1F - f * s);
        float f3 = v * (1F - (1F - f) * s);
        float f4;
        float f5;
        float f6;
        switch (i) {
            case 0 -> {
                f4 = v;
                f5 = f3;
                f6 = f1;
            }
            case 1 -> {
                f4 = f2;
                f5 = v;
                f6 = f1;
            }
            case 2 -> {
                f4 = f1;
                f5 = v;
                f6 = f3;
            }
            case 3 -> {
                f4 = f1;
                f5 = f2;
                f6 = v;
            }
            case 4 -> {
                f4 = f3;
                f5 = f1;
                f6 = v;
            }
            case 5 -> {
                f4 = v;
                f5 = f1;
                f6 = f2;
            }
            default -> throw new RuntimeException("Something went wrong when converting from HSV to RGB. Input was " + h + ", " + s + ", " + v);
        }

        int r = Mth.clamp((int)(f4 * 255F), 0, 255);
        int g = Mth.clamp((int)(f5 * 255F), 0, 255);
        int b = Mth.clamp((int)(f6 * 255F), 0, 255);
        return new int[]{r, g, b};
    }

    public static int[] parseToColor(int color) {
        int r = (color >> 16) & 0xFF;
        int g = (color >> 8) & 0xFF;
        int b = color & 0xFF;
        int a = (color >> 24) & 0xff;
        return new int[]{r, g, b, a};
    }

    public static double numberFormat(double number, int mf){
        NumberFormat format = NumberFormat.getNumberInstance();
        format.setMaximumFractionDigits(mf);
        return Double.parseDouble(format.format(number));
    }

    public static int getPower(int number, int power){
        if(power == 0) return 1;
        if(power == 1) return number;
        return number * getPower(number, power - 1);
    }
}
