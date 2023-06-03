package edebe.doglib;

import net.minecraftforge.common.ForgeConfigSpec;

public class DogLibConfig {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.IntValue TOO_EXPENSIVE_LEVEL;

    static {
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.comment("General configuration settings").push("general");
        TOO_EXPENSIVE_LEVEL = COMMON_BUILDER.comment("anvil too expensive level : -1 disabled").defineInRange("tooExpensiveLevel", 40, -1, Integer.MAX_VALUE);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
