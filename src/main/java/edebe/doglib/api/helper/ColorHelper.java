package edebe.doglib.api.helper;

import java.awt.*;

public interface ColorHelper {
    Color MIN_VALUE = new Color(0, 0, 0, 0);
    Color MAX_VALUE = new Color(255, 255, 255, 255);

    static Color hsvToRGB(int h, int s, int v) {
        return hsvToRGB(h, s, v, 255);
    }

    static Color hsvToRGB(int h, int s, int v, int a) {
        if (s > 100) s = 100;
        if (v > 100) v = 100;
        int[] rgb = MathHelper.hsvToRgb(h % 360 / 360F, s / 100F, v / 100F);
        return new Color(rgb[0], rgb[1], rgb[2], a);
    }

    static Color parseToColor(int color) {
        int[] rgba = MathHelper.parseToColor(color);
        return new Color(rgba[0], rgba[1], rgba[2], rgba[3]);
    }

    static Color parseToColor(int color, Color override, ColorOverrideMode... modes) {
        boolean red = false;
        boolean green = false;
        boolean blue = false;
        boolean alpha = false;
        for (ColorOverrideMode mode : modes) switch (mode) {
                case RED -> red = true;
                case GREEN -> green = true;
                case BLUE -> blue = true;
                case ALPHA -> alpha = true;
        }
        int[] rgba = MathHelper.parseToColor(color);
        return new Color(red ? override.getRed() : rgba[0], green ? override.getGreen() : rgba[1], blue ? override.getBlue() : rgba[2], alpha ? override.getAlpha() : rgba[3]);
    }

    static Color setColorRGBA(Color color, Color override, ColorOverrideMode... modes) {
        boolean red = false;
        boolean green = false;
        boolean blue = false;
        boolean alpha = false;
        for (ColorOverrideMode mode : modes) switch (mode) {
            case RED -> red = true;
            case GREEN -> green = true;
            case BLUE -> blue = true;
            case ALPHA -> alpha = true;
        }
        return new Color((red ? override : color).getRed(), (green ? override : color).getGreen(), (blue ? override : color).getBlue(), (alpha ? override : color).getAlpha());
    }

    enum ColorOverrideMode {
        RED, GREEN, BLUE, ALPHA
    }
}
