package dev.murqin.reifuelinfo;

import net.fabricmc.api.ClientModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ReiFuelInfoMod implements ClientModInitializer {
    public static final String MOD_ID = "reifuelinfo";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        LOGGER.info("REI Fuel Info initialized!");
    }
}
