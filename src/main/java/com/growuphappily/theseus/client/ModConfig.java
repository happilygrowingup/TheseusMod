package com.growuphappily.theseus.client;

import net.minecraftforge.common.ForgeConfigSpec;

public class ModConfig {
    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec.BooleanValue WARNING_PROCEED;

    static{
        ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
        COMMON_BUILDER.push("general");
        WARNING_PROCEED = COMMON_BUILDER.comment("This will be true if the demo warning is proceed.").define("waring_proceed", false);
        COMMON_BUILDER.pop();
        COMMON_CONFIG = COMMON_BUILDER.build();
    }
}
