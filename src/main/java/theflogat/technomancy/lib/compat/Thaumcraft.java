package theflogat.technomancy.lib.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import theflogat.technomancy.common.blocks.air.BlockFakeAirNG;
import theflogat.technomancy.common.blocks.base.BlockCosmeticOpaque;
import theflogat.technomancy.common.blocks.base.TMBlocks;
import theflogat.technomancy.common.blocks.thaumcraft.dynamos.BlockEssentiaDynamo;
import theflogat.technomancy.common.blocks.thaumcraft.dynamos.BlockNodeDynamo;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockAdvDeconTable;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockBiomeMorpher;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockCondenser;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockEldritchConsumer;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockElectricBellows;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockFluxLamp;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockNodeGenerator;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockReconstructor;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockTCProcessor;
import theflogat.technomancy.common.blocks.thaumcraft.machines.BlockTeslaCoil;
import theflogat.technomancy.common.blocks.thaumcraft.storage.BlockCreativeJar;
import theflogat.technomancy.common.blocks.thaumcraft.storage.BlockEssentiaContainer;
import theflogat.technomancy.common.blocks.thaumcraft.storage.BlockReservoir;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.items.thaumcraft.ElectricWandUpdate;
import theflogat.technomancy.common.items.thaumcraft.ItemEssentiaDynamo;
import theflogat.technomancy.common.items.thaumcraft.ItemFusionFocus;
import theflogat.technomancy.common.items.thaumcraft.ItemNodeDynamo;
import theflogat.technomancy.common.items.thaumcraft.ItemPen;
import theflogat.technomancy.common.items.thaumcraft.ItemTHMaterial;
import theflogat.technomancy.common.items.thaumcraft.ItemWandCores;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileEssentiaDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.dynamos.TileNodeDynamo;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileAdvDeconTable;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEldritchConsumer;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileElectricBellows;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileFluxLamp;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileNodeGenerator;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileReconstructor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileWirelessCoil;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileCreativeJar;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaReservoir;
import theflogat.technomancy.lib.Conf;
import theflogat.technomancy.lib.Ids;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.handlers.CompatibilityHandler;
import theflogat.technomancy.lib.handlers.ResearchHandler;
import theflogat.technomancy.util.Loc;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.common.registry.GameRegistry;

public class Thaumcraft extends ModuleBase {
	private static Thaumcraft instance;
	
	public static boolean crooked;

	public static BiomeGenBase biomeMagicalForest;
	public static BiomeGenBase biomeTaint;
	public static BiomeGenBase biomeEerie;

	public static Constructor<? extends EntityFX> FXEssentiaTrailConst;
	public static Constructor<? extends EntityFX> FXLightningBoltConst;
	public static Constructor<? extends TileEntitySpecialRenderer> JarRenderConst;

	public static Class<?> TileAlchemyFurnace;
	public static Class<?> TileArcaneFurnace;
	public static Class<?> TileInfusionMatrix;
	public static Class<?> TileNode;
	public static Class<?> TileMirrorEssentia;
	public static Class<?> TileTube;
	public static Class<?> TileTable;
	public static Class<?> TileResearchTable;
	public static Class<?> ItemWandCasting;
	public static Class<?> ThaumcraftC;
	public static Class<?> CommonProxy;
	public static Class<?> PlayerKnowledge;

	public static Method getObjectTags;
	public static Method getBonusTags;
	
	public static Method getRandomBiomeTag;
	public static Method createNodeAt;
	public static Method wispFX3;
	public static Method defaultFractal;
	public static Method setType;
	public static Method setWidth;
	public static Method finalizeBolt;
	public static Method drainEssentia;

	public static Method setCap;
	public static Method setRod;
	//WandCasting
	public static Method getAspectsWithRoom;
	public static Method getVis;
	public static Method getMaxVis;
	public static Method addVis;
	//TileNode
	public static Method getAspects;
	public static Method getNodeModifier;
	public static Method getNodeType;
	//UtilsFX
	public static Method renderQuadCenteredFromTexture;
	public static Method drawTag;
	public static Method drawFloatyLine;
	//JarRender
	public static Method renderLiquid;
	//PlayerKnowledge
	public static Method setAspectPool;
	public static Method getAspectPoolFor;
	//CommonProxy
	public static Method getPlayerKnowledge;
	
	public static Fluid FLUXGOO;

	public static Item itemResource;
	public static Item itemEssence;
	public static Item itemNugget;
	public static Item itemShard;
	public static Item itemWandRod;
	public static Item itemWandCap;
	public static Item itemWandCasting;
	public static Item itemPickThaumium;

	public static Block blockCosmeticSolid;
	public static Block blockMetalDevice;
	public static Block blockStoneDevice;
	public static Block blockJar;
	public static Block blockTube;
	public static Block blockCustomPlant;
	public static Block blockWoodenDevice;
	public static Block blockTable;

	public static int enchantFrugal;

	public static IIcon iconLiquid;

	public static SimpleNetworkWrapper PHInstance;
	public static Class<?> PacketFXEssentiaSource;
	public static Constructor<?> PacketFXEssentiaSourceConst;
	
	public static WandRod WAND_ROD_ELECTRIC;

	private Thaumcraft() {}
	
	public static Thaumcraft getInstance() {
		if(instance == null) {
			instance = new Thaumcraft();
		}
		return instance;
	}
	
	@SuppressWarnings("unchecked")
	public static void client() {
		try{
			Class<?> TPR = Class.forName("thaumcraft.client.ClientProxy");
			for(Method method : TPR.getMethods()){
				if(method.getName().equalsIgnoreCase("wispFX3")){wispFX3 = method;}
			}

			Class<?> FXEssentiaTrail = Class.forName("thaumcraft.client.fx.particles.FXEssentiaTrail");
			FXEssentiaTrailConst = (Constructor<? extends EntityFX>)FXEssentiaTrail.getConstructor(World.class, double.class, double.class, double.class, double.class,
					double.class, double.class, int.class, int.class, float.class);

			Class<?> FXLightningBolt = Class.forName("thaumcraft.client.fx.bolt.FXLightningBolt");
			FXLightningBoltConst = (Constructor<? extends EntityFX>)FXLightningBolt.getConstructor(World.class, double.class, double.class, double.class, double.class,
					double.class, double.class, long.class, int.class, float.class);
			for(Method method : FXLightningBolt.getMethods()){
				if(method.getName().equalsIgnoreCase("defaultFractal")){defaultFractal = method;
				}else if(method.getName().equalsIgnoreCase("setType")){setType = method;
				}else if(method.getName().equalsIgnoreCase("setWidth")){setWidth = method;
				}else if(method.getName().equalsIgnoreCase("finalizeBolt")){finalizeBolt = method;
				}
			}
			
			Class<?> UtilsFX = Class.forName("thaumcraft.client.lib.UtilsFX");

			for(Method method : UtilsFX.getMethods()){
				if(method.getName().equalsIgnoreCase("renderQuadCenteredFromTexture") && method.getParameterTypes()[0]==String.class){
					renderQuadCenteredFromTexture = method;
				}else if(method.getName().equalsIgnoreCase("drawTag") && method.getParameterTypes().length==3){
					drawTag = method;
				}else if(method.getName().equalsIgnoreCase("drawFloatyLine") && method.getParameterTypes().length==11){
					drawFloatyLine = method;
				}
			}
			
			Class<?> JR = Class.forName("thaumcraft.client.renderers.tile.TileJarRenderer");
			for(Method method : JR.getMethods()){
				if(method.getName().equalsIgnoreCase("renderLiquid")){renderLiquid = method;
				//}else if(method.getName().equalsIgnoreCase("drawTag")){drawTag = method;
				}
			}
			JarRenderConst = (Constructor<? extends TileEntitySpecialRenderer>)JR.getConstructor();
			System.out.println("Technomancy: Thaumcraft Client-Side Module loaded");
		}catch(Exception e){CompatibilityHandler.th = false;System.out.println("Technomancy: Failed to load Thaumcraft Client-Side Module");Conf.ex(e);}
	}

	public static void wispFX3(World worldObj, double posX, double posY, double posZ, double posX2, double posY2, double posZ2,
			float size, int type, boolean shrink, float gravity) {
		try{
			Class<?> TH = Class.forName("thaumcraft.common.Thaumcraft");
			wispFX3.invoke(TH.getField("proxy").get(TH), worldObj, posX, posY, posZ, posX2, posY2, posZ2, size, type, shrink, gravity);
		}catch(Exception e){e.printStackTrace();}
	}

	public static TileEntity getConnectableTile(World world, int x, int y, int z, ForgeDirection face){
		TileEntity te = world.getTileEntity(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
		if (!(te instanceof TileFakeAirNG) && ((te instanceof IEssentiaTransport)) && (((IEssentiaTransport)te).isConnectable(face.getOpposite()))) {
			return te;
		}
		return null;
	}
	
	public static TileEntity getConnectableAsContainer(World world, int x, int y, int z, ForgeDirection face){
		TileEntity te = world.getTileEntity(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
		if (te instanceof IAspectContainer || (te instanceof IEssentiaTransport && ((IEssentiaTransport)te).isConnectable(face.getOpposite()))) {
			return te;
		}
		return null;
	}
	
	public static AspectList getObjectAspects(ItemStack is) {
		AspectList ot = null;
		try {
			if (getObjectTags == null) {
				Class<?> fake = Class.forName("thaumcraft.common.lib.crafting.ThaumcraftCraftingManager");
				getObjectTags = fake.getMethod("getObjectTags", new Class[] { ItemStack.class });
			}
			ot = (AspectList)getObjectTags.invoke(null, is);
		} catch (Exception e) {
			cpw.mods.fml.common.FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.lib.ThaumcraftCraftingManager method " +
					"getObjectTags", new Object[0]);
		}
		return ot;
	}

	public static AspectList getBonusTags(ItemStack itemStack, AspectList al) {
		try{
			return (AspectList) getBonusTags.invoke(null, itemStack, al); 
		}catch(Exception e){e.printStackTrace();}
		return null;
	}

	public static void addAspectsToPool(String owner, Aspect aspect, short i) {
		try{
			setAspectPool.invoke(PlayerKnowledge.cast(getPlayerKnowledge.invoke(CommonProxy.cast(ThaumcraftC.getField("proxy").get(null)))), owner, aspect, i); 
		}catch(Exception e){e.printStackTrace();}
	}

	public static short getAspectPoolFor(String owner, Aspect aspect) {
		try{
			return (Short) getAspectPoolFor.invoke(PlayerKnowledge.cast(getPlayerKnowledge.invoke(CommonProxy.cast(ThaumcraftC.getField("proxy").get(null)))),
					owner, aspect); 
		}catch(Exception e){e.printStackTrace();}
		return 0;
	}
	
	public static void smeltify() {
		try {
			if(Conf.bonus) {
				ThaumcraftApi.addSmeltingBonus("oreGold", new ItemStack(Items.gold_nugget, 9, 0));
				ThaumcraftApi.addSmeltingBonus("oreIron", new ItemStack(Thaumcraft.itemNugget, 9, 0));
				ThaumcraftApi.addSmeltingBonus("oreCinnabar", new ItemStack(Thaumcraft.itemNugget, 9, 5));
				ThaumcraftApi.addSmeltingBonus("oreCopper", new ItemStack(Thaumcraft.itemNugget, 9, 1));
				ThaumcraftApi.addSmeltingBonus("oreTin", new ItemStack(Thaumcraft.itemNugget, 9, 2));
				ThaumcraftApi.addSmeltingBonus("oreSilver", new ItemStack(Thaumcraft.itemNugget, 9, 3));
				ThaumcraftApi.addSmeltingBonus("oreLead", new ItemStack(Thaumcraft.itemNugget, 9, 4));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void Init() {
		try {
			Class<?> THW = Class.forName("thaumcraft.common.lib.world.ThaumcraftWorldGenerator");
			biomeMagicalForest = (BiomeGenBase) THW.getField("biomeMagicalForest").get(THW);
			biomeTaint = (BiomeGenBase) THW.getField("biomeTaint").get(THW);
			biomeEerie = (BiomeGenBase) THW.getField("biomeEerie").get(THW);

			Class<?> TCB = Class.forName("thaumcraft.common.config.ConfigBlocks");
			FLUXGOO = (Fluid)TCB.getField("FLUXGOO").get(TCB);
			blockCosmeticSolid = (Block)TCB.getField("blockCosmeticSolid").get(TCB);
			blockMetalDevice = (Block)TCB.getField("blockMetalDevice").get(TCB);
			blockStoneDevice = (Block)TCB.getField("blockStoneDevice").get(TCB);
			blockJar = (Block)TCB.getField("blockJar").get(TCB);
			blockTube = (Block)TCB.getField("blockTube").get(TCB);
			blockCustomPlant = (Block)TCB.getField("blockCustomPlant").get(TCB);
			blockWoodenDevice = (Block)TCB.getField("blockWoodenDevice").get(TCB);
			blockTable = (Block)TCB.getField("blockTable").get(TCB);

			Class<?> TCI = Class.forName("thaumcraft.common.config.ConfigItems");
			itemResource = (Item)TCI.getField("itemResource").get(TCI);
			itemEssence = (Item)TCI.getField("itemEssence").get(TCI);
			itemNugget = (Item)TCI.getField("itemNugget").get(TCI);
			itemShard = (Item)TCI.getField("itemShard").get(TCI);
			itemWandRod = (Item)TCI.getField("itemWandRod").get(TCI);
			itemWandCap = (Item)TCI.getField("itemWandCap").get(TCI);
			itemWandCasting = (Item)TCI.getField("itemWandCasting").get(TCI);
			itemPickThaumium = (Item)TCI.getField("itemPickThaumium").get(TCI);

			TileAlchemyFurnace = Class.forName("thaumcraft.common.tiles.TileAlchemyFurnace");
			TileArcaneFurnace = Class.forName("thaumcraft.common.tiles.TileArcaneFurnace");
			TileInfusionMatrix = Class.forName("thaumcraft.common.tiles.TileInfusionMatrix");
			TileNode = Class.forName("thaumcraft.common.tiles.TileNode");
			TileMirrorEssentia = Class.forName("thaumcraft.common.tiles.TileMirrorEssentia");
			TileTube = Class.forName("thaumcraft.common.tiles.TileTube");
			TileTable = Class.forName("thaumcraft.common.tiles.TileTable");
			TileResearchTable = Class.forName("thaumcraft.common.tiles.TileResearchTable");
			ThaumcraftC = Class.forName("thaumcraft.common.Thaumcraft");

			for(Method method : TileNode.getMethods()){
				if(method.getName().equalsIgnoreCase("getAspects")){getAspects = method;
				}else if(method.getName().equalsIgnoreCase("getNodeModifier")){getNodeModifier = method;
				}else if(method.getName().equalsIgnoreCase("getNodeType")){getNodeType = method;
				}
			}

			Class<?> TCH = Class.forName("thaumcraft.common.lib.crafting.ThaumcraftCraftingManager");
			for(Method method : TCH.getMethods()){
				if(method.getName().equalsIgnoreCase("getObjectTags")){getObjectTags = method;
				} else if(method.getName().equalsIgnoreCase("getBonusTags")){getBonusTags = method;}
			}

			Class<?> TBH = Class.forName("thaumcraft.common.lib.world.biomes.BiomeHandler");
			for(Method method : TBH.getMethods()){
				if(method.getName().equalsIgnoreCase("getRandomBiomeTag")){getRandomBiomeTag = method;}
			}

			Class<?> TWG = Class.forName("thaumcraft.common.lib.world.ThaumcraftWorldGenerator");
			for(Method method : TWG.getMethods()){
				if(method.getName().equalsIgnoreCase("createNodeAt")){createNodeAt = method;}
			}

			Class<?> TEH = Class.forName("thaumcraft.common.lib.events.EssentiaHandler");
			for(Method method : TEH.getMethods()){
				if(method.getName().equalsIgnoreCase("drainEssentia")){drainEssentia = method;}
			}
			
			enchantFrugal = ThaumcraftApi.enchantFrugal;//(int)TAPI.getField("enchantFrugal").getInt(TAPI);

			ItemWandCasting = Class.forName("thaumcraft.common.items.wands.ItemWandCasting");
			for(Method method : ItemWandCasting.getMethods()){
				if(method.getName().equalsIgnoreCase("setRod")){setRod = method;
				}else if(method.getName().equalsIgnoreCase("setCap")){setCap = method;
				}else if(method.getName().equalsIgnoreCase("getAspectsWithRoom")){getAspectsWithRoom = method;
				}else if(method.getName().equalsIgnoreCase("getVis")){getVis = method;
				}else if(method.getName().equalsIgnoreCase("getMaxVis")){getMaxVis = method;
				}else if(method.getName().equalsIgnoreCase("addVis")){addVis = method;
				}
			}
			
			PlayerKnowledge = Class.forName("thaumcraft.common.lib.research.PlayerKnowledge");
			for(Method method : PlayerKnowledge.getMethods()){
				if(method.getName().equalsIgnoreCase("setAspectPool")){setAspectPool = method;}
				else if(method.getName().equalsIgnoreCase("getAspectPoolFor")){getAspectPoolFor = method;}
			}
			
			CommonProxy = Class.forName("thaumcraft.common.CommonProxy");
			for(Method method : CommonProxy.getMethods()){
				if(method.getName().equalsIgnoreCase("getPlayerKnowledge")){getPlayerKnowledge = method;}
			}

			Class<?> Conf = Class.forName("thaumcraft.common.config.Config");
			crooked = Conf.getField("crooked").getBoolean(Conf);
			
			Class<?> BlockJar = Class.forName("thaumcraft.common.blocks.BlockJar");
			iconLiquid = (IIcon)BlockJar.getField("iconLiquid").get(Thaumcraft.blockJar);
			
			Class<?> TPH = Class.forName("thaumcraft.common.lib.network.PacketHandler");
			PHInstance = (SimpleNetworkWrapper) TPH.getField("INSTANCE").get(TPH);
			
			Class<?> TES = Class.forName("thaumcraft.common.lib.network.fx.PacketFXEssentiaSource");
			// Constructor: public PacketFXEssentiaSource(int x, int y, int z, byte dx, byte dy, byte dz, int color)
			PacketFXEssentiaSourceConst = TES.getDeclaredConstructor(int.class, int.class, int.class, byte.class, byte.class, byte.class, int.class);
			
			System.out.println("Technomancy: Thaumcraft Module Activated");
		}catch(Exception e){CompatibilityHandler.th = false;System.out.println("Technomancy: Failed to load Thaumcraft Module");Conf.ex(e);}
		
		if(Loc.isClient()){
			client();
		}
	}

	@Override
	public void PostInit() {
		ResearchHandler.init();
		smeltify();		
	}

	@Override
	public void RegisterItems() {
        // Item Initializations
        //essentiaCannon = new ItemEssentiaCannon(ItemIds.idESSENTIA_CANNON);
        TMItems.itemMaterial = Ids.itemMaterial ? new ItemTHMaterial() : null;
        TMItems.itemPen = Ids.pen ? new ItemPen() : null;
        TMItems.itemWandCores = Ids.wandCores ? new ItemWandCores() : null;
        TMItems.itemFusionFocus = Ids.focusFusion ? new ItemFusionFocus() : null;
        
        //Registry
        //GameRegistry.registerItem(essentiaCannon, LibNames.ESSENTIA_CANNON_NAME);
        registerItem(TMItems.itemMaterial, Names.itemMaterial);
        registerItem(TMItems.itemPen, Names.pen);
        registerItem(TMItems.itemFusionFocus, Names.fusionFocus);
        
        registerItem(TMItems.itemWandCores, Names.wandCores);
        if(Ids.wandCores) {
        	WAND_ROD_ELECTRIC = new WandRod("electric", 25, new ItemStack(TMItems.itemWandCores, 1, 0), 9, new ElectricWandUpdate(), new ResourceLocation("technom", "textures/models/electricWand.png"));
        }
	}

	@Override
	public void RegisterBlocks() {
		TMBlocks.nodeDynamo = Ids.dynNode ? new BlockNodeDynamo() : null;
		TMBlocks.essentiaContainer = Ids.contEssentia ? new BlockEssentiaContainer() : null;
		TMBlocks.cosmeticOpaque = Ids.cosmeticOpaque ? new BlockCosmeticOpaque() : null;
		TMBlocks.essentiaDynamo = Ids.dynEssentia ? new BlockEssentiaDynamo() : null;
		TMBlocks.biomeMorpher = Ids.biomeMorpher ? new BlockBiomeMorpher() : null;
		TMBlocks.nodeGenerator = Ids.nodeGen ? new BlockNodeGenerator() : null;
		TMBlocks.fakeAirNG = Ids.nodeGen ? new BlockFakeAirNG() : null;
		TMBlocks.fluxLamp = Ids.fluxLamp ? new BlockFluxLamp() : null;
		TMBlocks.teslaCoil = Ids.wirelessCoil ? new BlockTeslaCoil() : null;
		TMBlocks.electricBellows = Ids.electricBellows ? new BlockElectricBellows() : null;
		TMBlocks.creativeJar = Ids.creativeJar ? new BlockCreativeJar() : null;
		TMBlocks.reconstructorBlock = Ids.reconstructor ? new BlockReconstructor() : null;
		TMBlocks.condenserBlock = Ids.condenser ? new BlockCondenser() : null;
		TMBlocks.processorTC = Ids.processorTC ? new BlockTCProcessor() : null;
		TMBlocks.eldritchConsumer = Ids.eldrichConsumer ? new BlockEldritchConsumer() : null;
		TMBlocks.reservoir = Ids.reservoir ? new BlockReservoir() : null;
		TMBlocks.advDeconTable = Ids.advDeconTable ? new BlockAdvDeconTable() : null;
		
		registerBlock(TMBlocks.nodeDynamo, Names.nodeDynamo, ItemNodeDynamo.class);
		registerBlock(TMBlocks.essentiaContainer, Names.essentiaContainer);
		registerBlock(TMBlocks.cosmeticOpaque, Names.cosmeticOpaque);
		registerBlock(TMBlocks.essentiaDynamo, Names.essentiaDynamo, ItemEssentiaDynamo.class);
		registerBlock(TMBlocks.biomeMorpher, Names.biomeMorpher);
		registerBlock(TMBlocks.nodeGenerator, Names.nodeGenerator);
		registerBlock(TMBlocks.fakeAirNG, Names.fakeAirNG);
		registerBlock(TMBlocks.fluxLamp, Names.fluxLamp);
		registerBlock(TMBlocks.teslaCoil, Names.teslaCoil);
		registerBlock(TMBlocks.electricBellows, Names.electricBellows);
		registerBlock(TMBlocks.creativeJar, Names.creativeJar);
		registerBlock(TMBlocks.reconstructorBlock, Names.reconstructor);
		registerBlock(TMBlocks.condenserBlock, Names.condenserBlock);
		registerBlock(TMBlocks.processorTC, Names.processor + "TC");
		registerBlock(TMBlocks.eldritchConsumer, Names.eldritchConsumer);
		registerBlock(TMBlocks.reservoir, Names.reservoir);
		registerBlock(TMBlocks.advDeconTable, Names.advDeconTable);
		
		registerTileEntity(TMBlocks.essentiaContainer, TileEssentiaContainer.class, "TileEssentiacontainer");
		registerTileEntity(TMBlocks.nodeDynamo, TileNodeDynamo.class, "TileNodeDynamo");
		registerTileEntity(TMBlocks.essentiaDynamo, TileEssentiaDynamo.class, "TileEssentiaDynamo");
		registerTileEntity(TMBlocks.biomeMorpher, TileBiomeMorpher.class, "TileBiomeMorpher");
		registerTileEntity(TMBlocks.nodeGenerator, TileNodeGenerator.class, "TileNodeGenerator");
		registerTileEntity(TMBlocks.fakeAirNG, TileFakeAirNG.class, Ref.MOD_PREFIX + "TileFakeAir");
		registerTileEntity(TMBlocks.fluxLamp, TileFluxLamp.class, "TileFluxLamp");
		registerTileEntity(TMBlocks.teslaCoil, TileWirelessCoil.class, "TileTeslaCoil");
		registerTileEntity(TMBlocks.electricBellows, TileElectricBellows.class, "TileElectricBellows");
		registerTileEntity(TMBlocks.creativeJar, TileCreativeJar.class, "TileCreativeJar");
		registerTileEntity(TMBlocks.reconstructorBlock, TileReconstructor.class, "TileReconstructor");
		registerTileEntity(TMBlocks.condenserBlock, TileCondenser.class, "TileCondenser");
		registerTileEntity(TMBlocks.processorTC, TileTCProcessor.class, "TileProcessorTC");
		registerTileEntity(TMBlocks.eldritchConsumer, TileEldritchConsumer.class, "TileEldrichConsumer");
		registerTileEntity(TMBlocks.reservoir, TileEssentiaReservoir.class, Ref.MOD_PREFIX + "TileEssentiaReservoir");
		registerTileEntity(TMBlocks.advDeconTable, TileAdvDeconTable.class, Ref.MOD_PREFIX + "TileAdvDeconTable");
	}

	@Override
	public void RegisterRecipes() {
		try {
			if(CompatibilityHandler.te) {
				//Infusion Recipes
				if(Ids.nodeGen) {
					ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR",
							new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
							new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
							new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block),
						new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
						new ItemStack(ThermalExpansion.blockCell, 1, 3)}));
				}
				if(Ids.fluxLamp) {
					ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
							new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
							new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
							new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
						new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald)}));
				}
				if(Ids.wandCores) {
					ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND",
							new ItemStack(TMItems.itemWandCores, 1, 0), 8,
							new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
									50),new ItemStack(Thaumcraft.itemWandRod, 1, 2),
									new ItemStack[] { new ItemStack((ThermalExpansion.capacitorResonant).getItem(), 1, 4), new ItemStack((ThermalExpansion.powerCoilElectrum)
											.getItem(), 1, 3), new ItemStack((ThermalExpansion.powerCoilSilver).getItem(), 1, 2),
											new ItemStack((ThermalExpansion.powerCoilGold).getItem(), 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				}
				if(Ids.condenser) {
					ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER",
							new ItemStack(TMBlocks.condenserBlock), 8,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
							.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
							new ItemStack[]{ThermalExpansion.frameMachineBasic,new ItemStack(TMItems.itemMaterial, 1, 1),new ItemStack(TMItems.itemMaterial,
									1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				}
				if(Ids.eldrichConsumer) {
					ResearchHandler.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER",
							new ItemStack(TMBlocks.eldritchConsumer, 1), 16,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
							.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
							new ItemStack[]{new ItemStack(Items.diamond_pickaxe),ThermalExpansion.powerCoilGold,new ItemStack(Thaumcraft.blockJar),
						new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));
				}

				//Arcane Recipes
				if(Ids.cosmeticOpaque) {
					ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
							new Object[] { "GG", "GG", 
						'G', new ItemStack(Blocks.glass)		}));
				}
				if(Ids.contEssentia) {
					ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
							new Object[] { "QNQ", "Q Q", "QQQ",
						'Q', new ItemStack(TMBlocks.cosmeticOpaque),
						'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				}
				if(Ids.dynNode) {
					ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO",
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
							.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
							new Object[] {" C ", "GIG", "IRI",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(TMItems.itemMaterial, 0),
						'R', new ItemStack(Items.redstone)				}));
				}
				if(Ids.dynEssentia) {
					ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
							new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
							new Object[] {" C ", "GIG", "IWI",
						'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));
				}
				if(Ids.biomeMorpher) {
					ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
							new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
							new Object[] {" E ", "TOB", "GCG",
						'E', new ItemStack(Items.emerald),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
						'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
						'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				}
				if(Ids.wirelessCoil) {
					ResearchHandler.recipes.put("TeslaCoil", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
							new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
							.add(Aspect.EARTH, 20),
							new Object[] {" N ", " C ", "TBT",
						'N', new ItemStack(TMItems.itemMaterial, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
					ResearchHandler.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
							new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
							new Object[] {" C ", " T ", " S ",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'S', new ItemStack(Items.stick)	}));
				}
				if(Ids.electricBellows) {
					ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS",
							new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
							new Object[] {"TG ", "EBC", "TG ",
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'E', ThermalExpansion.frameCellBasic,
						'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				}
				if(Ids.processorTC) {
					ResearchHandler.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
							new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
							new Object[] {" A ", "BMB", "ICI",
						'A', new ItemStack(Items.redstone),
						'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
						'M', ThermalExpansion.frameMachineBasic,
						'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				}

				//Crucible Recipes
				if(Ids.itemMaterial) {
					ResearchHandler.recipes.put("NeutronizedMetal", ThaumcraftApi.addCrucibleRecipe("THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0),
							new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));

					//Crafting Recipes
					ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
							new Object[] {"  N", " T ", "N  ", 
						'N', new ItemStack(Items.redstone), 
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2)	}));
					ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
							new Object[] {" T ", "TIT", " T ",
						'T', new ItemStack(TMItems.itemMaterial, 0),
						'I', new ItemStack(Items.iron_ingot)	}));
				}
				if(Ids.pen) {
					ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
							new Object[] {" NI", "NIN", "IN ",
						Character.valueOf('N'), "nuggetIron",
						Character.valueOf('I'), "dyeBlack"		}));
					
					ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
							new Object[] {			" IC", "IPI", "NI ",
						'I', new ItemStack(Items.iron_ingot),
						'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
						'P', new ItemStack(TMItems.itemMaterial, 1, 3),
						'N', new ItemStack(Items.gold_nugget)		}));
				}

				//Wand Recipes
				if(Ids.wandCores) {
					ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
					Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("thaumium"));
					Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
					ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60)
							.add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
							new Object[]{"  C", " R ", "C  ", 
						Character.valueOf('C'), WandCap.caps.get("thaumium").getItem(), 
						Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
					
					electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
					Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("void"));
					Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
					ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 87).add(Aspect.ORDER, 87)
							.add(Aspect.EARTH, 87).add(Aspect.FIRE, 87).add(Aspect.WATER, 87).add(Aspect.ENTROPY, 87), 
							new Object[]{"  C", " R ", "C  ", 
						Character.valueOf('C'), WandCap.caps.get("void").getItem(), 
						Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
				}
			} else {
				//Infusion Recipes
				if(Ids.nodeGen) {
					ResearchHandler.recipes.put("NodeGenerator", ThaumcraftApi.addInfusionCraftingRecipe("NODEGENERATOR",
							new ItemStack(TMBlocks.nodeGenerator, 1, 0), 20, 
							new AspectList().add(Aspect.AURA, 75).add(Aspect.ENERGY, 75).add(Aspect.TAINT, 75).add(Aspect.MAGIC, 75),
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), 
							new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(Blocks.diamond_block),
						new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
						new ItemStack(TMBlocks.essentiaContainer, 1, 0), new ItemStack(TMBlocks.essentiaContainer, 1, 0),
						new ItemStack(Blocks.gold_block, 1, 0)}));
				}
				if(Ids.fluxLamp) {
					ResearchHandler.recipes.put("FluxLamp", ThaumcraftApi.addInfusionCraftingRecipe("FLUXLAMP", new ItemStack(TMBlocks.fluxLamp), 10,
							new AspectList().add(Aspect.MECHANISM,  45).add(Aspect.TAINT, 45).add(Aspect.ORDER, 45).add(Aspect.LIGHT, 45),
							new ItemStack(Thaumcraft.blockMetalDevice, 1, 7),
							new ItemStack[] { new ItemStack(Thaumcraft.itemShard, 1 , 4), new ItemStack(Thaumcraft.itemShard, 1, 4),
						new ItemStack(Items.bucket), new ItemStack(Items.bucket), new ItemStack(Items.emerald), new ItemStack(Items.emerald)}));
				}
				if(Ids.wandCores) {
					ResearchHandler.recipes.put("EnergizedWandRod", ThaumcraftApi.addInfusionCraftingRecipe("ENERGIZEDWAND",
							new ItemStack(TMItems.itemWandCores, 1), 8,
							new AspectList().add(Aspect.ENERGY, 50).add(Aspect.TOOL, 50).add(Aspect.MAGIC, 50).add(Aspect.MECHANISM, 50).add(Aspect.EXCHANGE,
									50),new ItemStack(Thaumcraft.itemWandRod, 1, 2), new ItemStack[] { new ItemStack(Blocks.redstone_block, 1),
						new ItemStack(Blocks.gold_block, 1, 193),new ItemStack(TMItems.itemMaterial, 1, 2),
						new ItemStack(TMItems.itemMaterial, 1, 1), new ItemStack(TMItems.itemMaterial, 1, 1) }));
				}
				if(Ids.condenser) {
					ResearchHandler.recipes.put("Condenser", ThaumcraftApi.addInfusionCraftingRecipe("CONDENSER",
							new ItemStack(TMBlocks.condenserBlock), 8,	new AspectList().add(Aspect.ENERGY, 100).add(Aspect.MECHANISM, 50)
							.add(Aspect.EXCHANGE, 25).add(Aspect.ORDER, 25), new ItemStack(Thaumcraft.blockStoneDevice, 1 , 2), 
							new ItemStack[] { new ItemStack(TMItems.itemMaterial, 1, 2), new ItemStack(TMItems.itemMaterial, 1, 1),
						new ItemStack(TMItems.itemMaterial,1, 1), new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4),
						new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 4)}));
				}
				if(Ids.eldrichConsumer) {
					ResearchHandler.recipes.put("EldritchConsumer", ThaumcraftApi.addInfusionCraftingRecipe("ELDRITCHCONSUMER",
							new ItemStack(TMBlocks.eldritchConsumer, 1), 16,new AspectList().add(Aspect.EXCHANGE, 256).add(Aspect.MINE, 128)
							.add(Aspect.ENERGY, 64).add(Aspect.MOTION, 32), new ItemStack(Thaumcraft.blockMetalDevice, 1 , 9), 
							new ItemStack[]{new ItemStack(Items.diamond_pickaxe),new ItemStack(Items.redstone),new ItemStack(Thaumcraft.blockJar),
						new ItemStack(Thaumcraft.blockJar), new ItemStack(Thaumcraft.itemPickThaumium)}));
				}

				//Arcane Recipes
				if(Ids.cosmeticOpaque) {
					ResearchHandler.recipes.put("QuantumGlass", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.cosmeticOpaque,	4, 0), new AspectList().add(Aspect.ORDER, 5).add(Aspect.FIRE, 5), 
							new Object[] { "GG", "GG", 
						'G', new ItemStack(Blocks.glass)		}));
				}
				if(Ids.contEssentia) {
					ResearchHandler.recipes.put("QuantumJar", ThaumcraftApi.addArcaneCraftingRecipe("QUANTUMJARS",
							new ItemStack(TMBlocks.essentiaContainer, 1, 0), new AspectList().add(Aspect.ORDER, 15).add(Aspect.WATER, 10),
							new Object[] { "QNQ", "Q Q", "QQQ",
						'Q', new ItemStack(TMBlocks.cosmeticOpaque),
						'N', new ItemStack(TMItems.itemMaterial, 0)		}));
				}
				if(Ids.dynNode) {
					ResearchHandler.recipes.put("NodeDynamo", ThaumcraftApi.addArcaneCraftingRecipe("DYNAMO",
							new ItemStack(TMBlocks.nodeDynamo, 1, 0), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ORDER, 25)
							.add(Aspect.FIRE, 15).add(Aspect.ENTROPY, 10),
							new Object[] {" C ", "GIG", "IRI",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(TMItems.itemMaterial, 0),
						'R', new ItemStack(Items.redstone)				}));
				}
				if(Ids.dynEssentia) {
					ResearchHandler.recipes.put("EssentiaDynamo", ThaumcraftApi.addArcaneCraftingRecipe( "DYNAMO", new ItemStack(TMBlocks.essentiaDynamo, 1, 0),
							new AspectList().add(Aspect.WATER, 15).add(Aspect.ORDER, 10).add(Aspect.FIRE, 5).add(Aspect.ENTROPY, 25),
							new Object[] {" C ", "GIG", "IWI",
						'W', new ItemStack(Thaumcraft.blockJar, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'I', new ItemStack(Thaumcraft.blockTube, 1, 0),				}));
				}
				if(Ids.biomeMorpher) {
					ResearchHandler.recipes.put("BiomeMorpher", ThaumcraftApi.addArcaneCraftingRecipe("BIOMEMORPHER", new ItemStack(TMBlocks.biomeMorpher),
							new AspectList().add(Aspect.EARTH, 30).add(Aspect.ORDER, 30).add(Aspect.WATER, 30), 
							new Object[] {" E ", "TOB", "GCG",
						'E', new ItemStack(Items.emerald),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 11),
						'O', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 0),
						'B', new ItemStack(Thaumcraft.blockCustomPlant, 1, 4),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)		}));
				}
				if(Ids.wirelessCoil) {
					ResearchHandler.recipes.put("TeslaCoil", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMBlocks.teslaCoil),
							new AspectList().add(Aspect.WATER, 20).add(Aspect.ORDER, 20).add(Aspect.ENTROPY, 20).add(Aspect.AIR, 20).add(Aspect.FIRE, 20)
							.add(Aspect.EARTH, 20),
							new Object[] {" N ", " C ", "TBT",
						'N', new ItemStack(TMItems.itemMaterial, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'B', new ItemStack(Thaumcraft.blockTube, 1, 4)		}));
				}
				if(Ids.itemMaterial) {
					ResearchHandler.recipes.put("CoilCoupler", ThaumcraftApi.addArcaneCraftingRecipe("TESLACOIL", new ItemStack(TMItems.itemMaterial, 1, 4),
							new AspectList().add(Aspect.AIR, 10).add(Aspect.ORDER, 15),
							new Object[] {" C ", " T ", " S ",
						'C', new ItemStack(TMItems.itemMaterial, 1, 1),
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'S', new ItemStack(Items.stick)	}));
				}
				if(Ids.electricBellows) {
					ResearchHandler.recipes.put("ElectricBellows", ThaumcraftApi.addArcaneCraftingRecipe("ELECTRICBELLOWS",
							new ItemStack(TMBlocks.electricBellows, 1, 0), new AspectList().add(Aspect.AIR, 30).add(Aspect.ORDER, 30).add(Aspect.FIRE, 30),
							new Object[] {"TG ", "EBC", "TG ",
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'G', new ItemStack(TMItems.itemMaterial, 1, 2),
						'E', new ItemStack(Blocks.redstone_block, 1),
						'B', new ItemStack(Thaumcraft.blockWoodenDevice, 1, 0),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				}
				if(Ids.processorTC) {
					ResearchHandler.recipes.put("Processor", ThaumcraftApi.addArcaneCraftingRecipe("PROCESSOR", new ItemStack(TMBlocks.processorTC, 1, 0),
							new AspectList().add(Aspect.FIRE, 25).add(Aspect.ENTROPY, 25).add(Aspect.ORDER, 25), 
							new Object[] {" A ", "BMB", "ICI",
						'A', new ItemStack(Items.redstone),
						'B', new ItemStack(Thaumcraft.blockCosmeticSolid, 1, 6),
						'M', new ItemStack(Blocks.redstone_block, 1),
						'I', new ItemStack(Thaumcraft.itemResource, 1, 2),
						'C', new ItemStack(TMItems.itemMaterial, 1, 1)				}));
				}

				//Crucible Recipes
				if(Ids.itemMaterial) {
					ResearchHandler.recipes.put("NeutronizedMetal", ThaumcraftApi.addCrucibleRecipe("THAUMIUM",	new ItemStack(TMItems.itemMaterial, 1, 0),
							new ItemStack(Thaumcraft.itemResource, 1, 2), new AspectList().add(Aspect.ORDER, 2).add(Aspect.ENERGY, 2)));

					//Crafting Recipes
					ResearchHandler.recipes.put("MagicCoil", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 1), 
							new Object[] {"  N", " T ", "N  ", 
						'N', new ItemStack(Items.redstone), 
						'T', new ItemStack(Thaumcraft.itemResource, 1, 2)	}));
					ResearchHandler.recipes.put("NeutronizedGear", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemMaterial, 1, 2), 
							new Object[] {" T ", "TIT", " T ",
						'T', new ItemStack(TMItems.itemMaterial, 0),
						'I', new ItemStack(Items.iron_ingot)	}));
				}
				if(Ids.pen) {
					ResearchHandler.recipes.put("PenCore", oreDictRecipe(new ItemStack(TMItems.itemMaterial, 1, 3), 
							new Object[] {" NI", "NIN", "IN ",
						Character.valueOf('N'), "nuggetIron",
						Character.valueOf('I'), "dyeBlack"		}));
					
					ResearchHandler.recipes.put("Pen", GameRegistry.addShapedRecipe(new ItemStack(TMItems.itemPen, 1), 
							new Object[] {			" IC", "IPI", "NI ",
						'I', new ItemStack(Items.iron_ingot),
						'C', new ItemStack(Thaumcraft.itemWandCap, 1, 0),
						'P', new ItemStack(TMItems.itemMaterial, 1, 3),
						'N', new ItemStack(Items.gold_nugget)		}));
				}

				//Wand Recipes
				if(Ids.wandCores) {
					ItemStack electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
					Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("thaumium"));
					Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
					ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 60).add(Aspect.ORDER, 60)
							.add(Aspect.EARTH, 60).add(Aspect.FIRE, 60).add(Aspect.WATER, 60).add(Aspect.ENTROPY, 60), 
							new Object[]{"  C", " R ", "C  ", 
						Character.valueOf('C'), WandCap.caps.get("thaumium").getItem(), 
						Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
					
					electric = new ItemStack(Thaumcraft.itemWandCasting, 1, 72);
					Thaumcraft.setCap.invoke(electric.getItem(), electric, WandCap.caps.get("void"));
					Thaumcraft.setRod.invoke(electric.getItem(), electric, WandRod.rods.get("electric"));
					ThaumcraftApi.addArcaneCraftingRecipe("ENERGIZEDWAND", electric, new AspectList().add(Aspect.AIR, 87).add(Aspect.ORDER, 87)
							.add(Aspect.EARTH, 87).add(Aspect.FIRE, 87).add(Aspect.WATER, 87).add(Aspect.ENTROPY, 87), 
							new Object[]{"  C", " R ", "C  ", 
						Character.valueOf('C'), WandCap.caps.get("void").getItem(), 
						Character.valueOf('R'), WandRod.rods.get("electric").getItem()});
				}
			}
			
			if(Ids.advDeconTable) {
				ResearchHandler.recipes.put("AdvDeconTable", ThaumcraftApi.addInfusionCraftingRecipe("ADVDECONTABLE", new ItemStack(TMBlocks.advDeconTable),
						10, new AspectList().add(Aspect.MAGIC, 32).add(Aspect.CRAFT, 8).add(Aspect.EXCHANGE, 16).add(Aspect.TOOL, 16),
						new ItemStack(Thaumcraft.blockTable, 1, 14), new ItemStack[]{new ItemStack(Blocks.piston), new ItemStack(Blocks.piston),
						new ItemStack(Blocks.piston), new ItemStack(Items.emerald)}));
			}
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
}
