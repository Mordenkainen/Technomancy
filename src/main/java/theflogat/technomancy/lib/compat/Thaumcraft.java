package theflogat.technomancy.lib.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IAspectContainer;
import thaumcraft.api.aspects.IEssentiaTransport;
import theflogat.technomancy.common.tiles.air.TileFakeAirNG;
import theflogat.technomancy.lib.Conf;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class Thaumcraft {

	public static boolean th = true;
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

	public static void init() {
		try{
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
		}catch(Exception e){th = false;System.out.println("Technomancy: Failed to load Thaumcraft Module");Conf.ex(e);}
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
		}catch(Exception e){th = false;System.out.println("Technomancy: Failed to load Thaumcraft Client-Side Module");Conf.ex(e);}
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
}
