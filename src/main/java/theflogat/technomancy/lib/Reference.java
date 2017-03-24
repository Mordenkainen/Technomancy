package theflogat.technomancy.lib;

import java.util.Locale;

public final class Reference {
    
    // Mod Constants
    public static final String MOD_ID = "technom";
    public static final String MOD_PREFIX = "techno:";
    public static final String MOD_NAME = "Technomancy";
    public static final String MOD_VERSION = "0.12.5";
    public static final String CHANNEL_NAME = MOD_NAME;
    public static final String PROXY_LOC = "theflogat.technomancy.proxies.";

    // Locations
    public static final String GUI_PREFIX = MOD_ID + ":textures/gui/";
    public static final String MODEL_PREFIX = MOD_ID + ":textures/models/";
    public static final String TEXTURE_PREFIX = MOD_ID + ":";

    public static final String EXT = ".png";
    // Model Names
    public static final String MODEL_WHITE = MODEL_PREFIX + "white.png";
    public static final String MODEL_NODE_DYNAMO = MODEL_PREFIX + "nodeDynamo.png";
    public static final String MODEL_ESS_CONT = MODEL_PREFIX + "essentiaContainer.png";
    public static final String MODEL_ESS_DYNAMO = MODEL_PREFIX + "essentiaDynamo.png";
    public static final String MODEL_BIOME_MORPH = MODEL_PREFIX + "biomeMorpher.png";
    public static final String MODEL_NODE_GEN = MODEL_PREFIX + "nodeGenerator.png";
    public static final String MODEL_FLUX_LAMP = MODEL_PREFIX + "fluxLamp.png";
    public static final String MODEL_ESS_TRAN = MODEL_PREFIX + "essentiaCoil.png";
    public static final String MODEL_ITEM_TRAN = MODEL_PREFIX + "itemCoil.png";
    public static final String MODEL_BELLOWS = MODEL_PREFIX + "electricBellows.png";
    public static final String MODEL_RECON = MODEL_PREFIX + "reconstructor.png";
    public static final String MODEL_BLOOD_DYN = MODEL_PREFIX + "bloodDynamo.png";
    public static final String MODEL_BLOOD_FAB = MODEL_PREFIX + "bloodFabricator.png";
    public static final String MODEL_MANA_FAB = MODEL_PREFIX + "manaFabricator.png";
    public static final String MODEL_FLOWER_DYN = MODEL_PREFIX + "flowerDynamo.png";
    public static final String MODEL_CRYSTAL = MODEL_PREFIX + "blockCrystal.png";
    public static final String MODEL_ELD_CONS = MODEL_PREFIX + "eldCons.png";
    public static final String MODEL_SPHERE = MODEL_PREFIX + "sphere.png";
    public static final String MODEL_CATALYST = MODEL_PREFIX + "catalyst.png";
    public static final String MODEL_FUSOR = MODEL_PREFIX + "fusor.png";
    public static final String MODEL_FOUNT = MODEL_PREFIX + "fountain.png";
    public static final String MODEL_ANVIL = MODEL_PREFIX + "anvil.png";
    public static final String MODEL_BRF_TEXTURE = MODEL_PREFIX + "bottomRF.png";
    public static final String MODEL_EXISTENCE = MODEL_PREFIX + "existence.png";
    public static final String HUD_TEXTURE = MODEL_PREFIX + "hud.png";
    public static final String HUD_EX_TEXTURE = MODEL_PREFIX + "exHud.png";

    // GUI Textures
    public static final String GUI_BM_PROCESSOR = GUI_PREFIX + "processorBM.png";
    public static final String GUI_BO_PROCESSOR = GUI_PREFIX + "processorBO.png";
    public static final String GUI_TC_PROCESSOR = GUI_PREFIX + "processorTC.png";

    private Reference() {}
    
    public static String getId(final String str) {
        return MOD_PREFIX.toLowerCase(Locale.US) + str;
    }

    public static String getAsset(final String str) {
        return TEXTURE_PREFIX + str;
    }

    public static String getGui(final String str) {
        return GUI_PREFIX + str;
    }
}
