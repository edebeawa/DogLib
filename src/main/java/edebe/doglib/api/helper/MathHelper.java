package edebe.doglib.api.helper;

import java.text.NumberFormat;

public final class MathHelper {
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
