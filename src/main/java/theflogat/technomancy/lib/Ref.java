package theflogat.technomancy.lib;

public class Ref {

    // Mod Constants
    public static final String MOD_ID = "technom";
    public static final String MOD_PREFIX = "techno:";
    public static final String MOD_NAME = "Technomancy";
    public static final String MOD_VERSION = "1.12-1.0.1";
    public static final String CHANNEL_NAME = MOD_NAME;
    public static final String proxy_loc = "theflogat.technomancy.proxies.";
    
    //Locations
    public static final String GUI_PREFIX = MOD_ID + ":textures/gui/";
    public static final String MODEL_PREFIX = MOD_ID + ":textures/models/";
    public static final String TEXTURE_PREFIX = MOD_ID + ":";
    
    public static final String EXT = ".png";
    //Model Names
	public static final String MODEL_TEST_TEXTURE = MODEL_PREFIX + "test.png";
	public static final String MODEL_WHITE_TEXTURE = MODEL_PREFIX + "white.png";
    public static final String MODEL_NODE_DYNAMO_TEXTURE = MODEL_PREFIX + "nodeDynamo.png";
	public static final String MODEL_ESSENTIA_CONTAINER_TEXTURE = MODEL_PREFIX + "essentiaContainer.png";
	public static final String MODEL_ESSENTIA_DYNAMO_TEXTURE =  MODEL_PREFIX + "essentiaDynamo.png";
	public static final String MODEL_BIOME_MODIFIER_TEXTURE = MODEL_PREFIX + "biomeMorpher.png";
	public static final String MODEL_NODE_GENERATOR_TEXTURE = MODEL_PREFIX + "nodeGenerator.png";
	public static final String MODEL_FLUX_LAMP_TEXTURE = MODEL_PREFIX + "fluxLamp.png";
	public static final String MODEL_ESSENTIA_TRANSMITTER_TEXTURE = MODEL_PREFIX + "essentiaCoil.png";
	public static final String MODEL_ITEM_TRANSMITTER_TEXTURE = MODEL_PREFIX + "itemcoil.png";
	public static final String MODEL_ELECTRIC_BELLOWS_TEXTURE = MODEL_PREFIX + "electricBellows.png";
	public static final String MODEL_RECONSTRUCTOR_TEXTURE = MODEL_PREFIX + "reconstructor.png";
	public static final String MODEL_BLOOD_DYNAMO_TEXTURE = MODEL_PREFIX + "blooddynamo.png";
	public static final String MODEL_BLOOD_FABRICATOR_TEXTURE = MODEL_PREFIX + "bloodfabricator.png";
	public static final String MODEL_MANA_FABRICATOR_TEXTURE = MODEL_PREFIX + "manafabricator.png";
	public static final String MODEL_FLOWER_DYANMO_TEXTURE = MODEL_PREFIX + "flowerdynamo.png";
	public static final String MODEL_CRYSTAL_TEXTURE = MODEL_PREFIX + "blockCrystal.png";
	public static final String MODEL_ELDRITCH_CONSUMER_TEXTURE = MODEL_PREFIX + "eldCons.png";
	public static final String MODEL_REF_TEXTURE = MODEL_PREFIX + "sphere.png";
	public static final String MODEL_CATALYST_TEXTURE = MODEL_PREFIX + "catalyst.png";
	public static final String MODEL_FUSOR_TEXTURE = MODEL_PREFIX + "fusor.png";
	public static final String MODEL_COBBLEM_TEXTURE = MODEL_PREFIX + "fountain.png";
	public static final String MODEL_ANVIL_TEXTURE = MODEL_PREFIX + "anvil.png";
	public static final String MODEL_BRF_TEXTURE = MODEL_PREFIX + "bottomRF.png";
	public static final String MODEL_EXISTENCE_TEXTURE = MODEL_PREFIX + "existence.png";
	public static final String HUD_TEXTURE = MODEL_PREFIX + "hud.png";
	public static final String HUD_EX_TEXTURE = MODEL_PREFIX + "exHud.png";
	
	//GUI Textures
	public static final String GUI_BM_PROCESSOR_TEXTURE = GUI_PREFIX + "processorBM.png";
	public static final String GUI_BO_PROCESSOR_TEXTURE = GUI_PREFIX + "processorBO.png";
	public static final String GUI_TC_PROCESSOR_TEXTURE = GUI_PREFIX + "processorTC.png";
	
	public static String getId(String str) {
		return MOD_PREFIX.toLowerCase() + str;
	}

	public static String getAsset(String str) {
		return TEXTURE_PREFIX + str;
	}
	
	public static String getGui(String str) {
		return GUI_PREFIX + str;
	}
}
