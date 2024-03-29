package edebe.doglib.api.helper;

public final class TextHelper {
    private static final String[] thousands = {"", "M", "MM", "MMM"};
    private static final String[] hundreds = {"", "C", "CC", "CCC", "CD", "D", "DC", "DCC", "DCCC", "CM"};
    private static final String[] tens = {"", "X", "XX", "XXX", "XL", "L", "LX", "LXX", "LXXX", "XC"};
    private static final String[] ones = {"", "I", "II", "III", "IV", "V", "VI", "VII", "VIII", "IX"};

    public static String numberToRoman(int number) {
        return thousands[number / 1000] +
        hundreds[number % 1000 / 100] +
        tens[number % 100 / 10] +
        ones[number % 10];
    }
}
