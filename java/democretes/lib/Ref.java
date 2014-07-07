package democretes.lib;

public class Ref {

    // Mod Constants
    public static final String MOD_ID = "technom";
    public static final String MOD_PREFIX = "techno:";
    public static final String MOD_NAME = "Technomancy";
    public static final String MOD_VERSION = "0.7.0";
    public static final String CHANNEL_NAME = MOD_NAME;
    
    //Locations
    public static final String MODEL_PREFIX = MOD_ID + ":textures/models/";
    public static final String TEXTURE_PREFIX = MOD_ID + ":";
    
    //Model Names
    public static final String MODEL_NODE_DYNAMO_TEXTURE = MODEL_PREFIX + "nodeDynamo.png";
	public static final String MODEL_ESSENTIA_CONTAINER_TEXTURE = MODEL_PREFIX + "essentiaContainer.png";
	public static final String MODEL_ESSENTIA_DYNAMO_TEXTURE =  MODEL_PREFIX + "essentiaDynamo.png";
	public static final String MODEL_BIOME_MODIFIER_TEXTURE = MODEL_PREFIX + "biomeMorpher.png";
	public static final String MODEL_NODE_GENERATOR_TEXTURE = MODEL_PREFIX + "nodeGenerator.png";
	public static final String MODEL_FLUX_LAMP_TEXTURE = MODEL_PREFIX + "fluxLamp.png";
	public static final String MODEL_TESLA_COIL_TEXTURE = MODEL_PREFIX + "essentiaCoil.png";
	public static final String MODEL_ELECTRIC_BELLOWS_TEXTURE = MODEL_PREFIX + "electricBellows.png";
	public static final String MODEL_RECONSTRUCTOR_TEXTURE = MODEL_PREFIX + "reconstructor.png";
	public static final String MODEL_BLOOD_DYNAMO_TEXTURE = MODEL_PREFIX + "bloodDynamo.png";
	public static final String MODEL_BLOOD_FABRICATOR_TEXTURE = MODEL_PREFIX + "bloodFabricator.png";
	public static final String MODEL_MANA_FABRICATOR_TEXTURE = MODEL_PREFIX + "manaFabricator.png";
	public static final String MODEL_FLOWER_DYANMO_TEXTURE = MODEL_PREFIX + "flowerDynamo.png";
	
	public static String getAsset(String str) {
		return MOD_ID.toLowerCase() + ":" + str;
	}
	
	public static String getId(String str) {
		return MOD_ID.toLowerCase() + ":" + str;
	}
}
