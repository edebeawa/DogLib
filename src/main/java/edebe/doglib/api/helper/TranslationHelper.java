package edebe.doglib.api.helper;

import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;

import java.util.*;

public class TranslationHelper {
    public static MutableComponent translationProcessing(String translation, Map<String, ?> regexMap) {
        Map<String, Object> map = regexMap();
        if (regexMap != null && !map.isEmpty()) map.putAll(regexMap);
        String[] keys = map.keySet().toArray(new String[]{});
        Object[] values = map.values().toArray(new Object[]{});;
        for (int i = 0; i < keys.length; i++)
            translation = translation.replaceAll(keys[i], values[i].toString());
        return Component.literal(translation);
    }

    private static Map<String, Object> regexMap() {
        Map<String, Object> regexp = new LinkedHashMap<>();
        putElement(regexp, "P", "%");
        return regexp;
    }

    public static <T> void putElement(Map<String, T> map, String element, T value) {
        map.put(createElement(element), value);
    }

    public static String createElement(String element) {
        return "\\$\\(" + element + "\\)";
    }

    public static String getTranslation(String key) {
        return Component.translatable(key).getString();
    }

    public static String getTranslation(String key, Object... args) {
        return Component.translatable(key, args).getString();
    }

    public static MutableComponent getTranslationProcessed(String key) {
        return translationProcessing(getTranslation(key), null);
    }

    public static MutableComponent getTranslationProcessed(String key, Object... args) {
        return translationProcessing(getTranslation(key, args), null);
    }

    public static MutableComponent getTranslationCustomProcessed(String key, Map<String, ?> argsMap) {
        return translationProcessing(getTranslation(key), argsMap);
    }
}