package democretes.compat;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.ForgeDirection;
import net.minecraftforge.fluids.Fluid;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaTransport;

public class Thaumcraft {

	public static boolean th = true;
	public static boolean crooked;

	public static BiomeGenBase biomeMagicalForest;
	public static BiomeGenBase biomeTaint;
	public static BiomeGenBase biomeEerie;

	public static Constructor<? extends EntityFX> FXEssentiaTrailConst;
	public static Constructor<? extends EntityFX> FXLightningBoltConst;
	public static Constructor<? extends TileEntitySpecialRenderer> JarRenderConst;
	public static Constructor<?> ResearchItemConst;
	public static Constructor<?> ResearchItemStConst;
	public static Constructor<?> ResearchPageString;
	public static Constructor<?> ResearchPageCrucible;
	public static Constructor<?> ResearchPageIRecipe;
	public static Constructor<?> ResearchPageIArcRecipe;
	public static Constructor<?> ResearchPageIInfRecipe;

	public static Class TileAlchemyFurnace;
	public static Class TileArcaneFurnace;
	public static Class TileInfusionMatrix;
	public static Class TileNode;
	public static Class TileMirrorEssentia;
	public static Class TileTube;
	public static Class ItemWandCasting;
	public static Class ResearchPage;
	public static Class ResearchPageArr;
	public static Class CrucibleRecipe;
	public static Class ArcaneRecipe;
	public static Class InfusionRecipe;

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
	//ThaumcraftApi
	public static Method addSmeltingBonus;
	public static Method addInfusionCraftingRecipe;
	public static Method addArcaneCraftingRecipe;
	public static Method addCrucibleRecipe;

	public static Method setCap;
	public static Method setRod;
	//ResearchCategory
	public static Method registerCategory;
	//PageResearch
	public static Method setPages;
	public static Method setAutoUnlock;
	public static Method setRound;
	public static Method setStub;
	public static Method registerResearchItem;
	public static Method setParents;
	public static Method setSecondary;
	public static Method setSpecial;
	public static Method setHidden;
	public static Method setItemTriggers;
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
	//JarRender
	public static Method renderLiquid;

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

	public static int enchantFrugal;

	public static Icon iconLiquid;



	public static void init() {
		try{
			Class THW = Class.forName("thaumcraft.common.lib.world.ThaumcraftWorldGenerator");
			biomeMagicalForest = (BiomeGenBase) THW.getField("biomeMagicalForest").get(THW);
			biomeTaint = (BiomeGenBase) THW.getField("biomeTaint").get(THW);
			biomeEerie = (BiomeGenBase) THW.getField("biomeEerie").get(THW);

			Class TCB = Class.forName("thaumcraft.common.config.ConfigBlocks");
			FLUXGOO = (Fluid)TCB.getField("FLUXGOO").get(TCB);
			blockCosmeticSolid = (Block)TCB.getField("blockCosmeticSolid").get(TCB);
			blockMetalDevice = (Block)TCB.getField("blockMetalDevice").get(TCB);
			blockStoneDevice = (Block)TCB.getField("blockStoneDevice").get(TCB);
			blockJar = (Block)TCB.getField("blockJar").get(TCB);
			blockTube = (Block)TCB.getField("blockTube").get(TCB);
			blockCustomPlant = (Block)TCB.getField("blockCustomPlant").get(TCB);
			blockWoodenDevice = (Block)TCB.getField("blockWoodenDevice").get(TCB);

			Class TCI = Class.forName("thaumcraft.common.config.ConfigItems");
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

			for(Method method : TileNode.getMethods()){
				if(method.getName().equalsIgnoreCase("getAspects")){getAspects = method;
				}else if(method.getName().equalsIgnoreCase("getNodeModifier")){getNodeModifier = method;
				}else if(method.getName().equalsIgnoreCase("getNodeType")){getNodeType = method;
				}
			}

			Class TCH = Class.forName("thaumcraft.common.lib.ThaumcraftCraftingManager");
			for(Method method : TCH.getMethods()){
				if(method.getName().equalsIgnoreCase("getObjectTags")){getObjectTags = method;
				} else if(method.getName().equalsIgnoreCase("getBonusTags")){getBonusTags = method;}
			}

			Class TBH = Class.forName("thaumcraft.common.lib.world.biomes.BiomeHandler");
			for(Method method : TBH.getMethods()){
				if(method.getName().equalsIgnoreCase("getRandomBiomeTag")){getRandomBiomeTag = method;}
			}

			Class TWG = Class.forName("thaumcraft.common.lib.world.ThaumcraftWorldGenerator");
			for(Method method : TWG.getMethods()){
				if(method.getName().equalsIgnoreCase("createNodeAt")){createNodeAt = method;}
			}

			Class TPR = Class.forName("thaumcraft.common.CommonProxy");
			for(Method method : TPR.getMethods()){
				if(method.getName().equalsIgnoreCase("wispFX3")){wispFX3 = method;}
			}

			Class TEH = Class.forName("thaumcraft.common.lib.EssentiaHandler");
			for(Method method : TEH.getMethods()){
				if(method.getName().equalsIgnoreCase("drainEssentia")){drainEssentia = method;}
			}

			Class FXEssentiaTrail = Class.forName("thaumcraft.client.fx.FXEssentiaTrail");
			FXEssentiaTrailConst = FXEssentiaTrail.getConstructor(World.class, double.class, double.class, double.class, double.class,
					double.class, double.class, int.class, int.class, float.class);

			Class FXLightningBolt = Class.forName("thaumcraft.client.fx.FXLightningBolt");
			FXLightningBoltConst = FXLightningBolt.getConstructor(World.class, double.class, double.class, double.class, double.class,
					double.class, double.class, long.class, int.class, float.class);
			for(Method method : FXLightningBolt.getMethods()){
				if(method.getName().equalsIgnoreCase("defaultFractal")){defaultFractal = method;
				}else if(method.getName().equalsIgnoreCase("setType")){setType = method;
				}else if(method.getName().equalsIgnoreCase("setWidth")){setWidth = method;
				}else if(method.getName().equalsIgnoreCase("finalizeBolt")){finalizeBolt = method;
				}
			}

			Class TAPI = Class.forName("thaumcraft.api.ThaumcraftApi");
			for(Method method : TAPI.getMethods()){
				if(method.getName().equalsIgnoreCase("addSmeltingBonus") && method.getParameterTypes()[0]!=ItemStack.class){
					addSmeltingBonus = method;
				}else if(method.getName().equalsIgnoreCase("addInfusionCraftingRecipe")){addInfusionCraftingRecipe = method;
				}else if(method.getName().equalsIgnoreCase("addArcaneCraftingRecipe")){addArcaneCraftingRecipe = method;
				}else if(method.getName().equalsIgnoreCase("addCrucibleRecipe")){addCrucibleRecipe = method;
				}
			}
			enchantFrugal = (int)TAPI.getField("enchantFrugal").getInt(TAPI);

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

			Class RCat = Class.forName("thaumcraft.api.research.ResearchCategories");
			for(Method method : RCat.getMethods()){
				if(method.getName().equalsIgnoreCase("registerCategory")){registerCategory = method;
				}
			}

			Class TRI = Class.forName("thaumcraft.api.research.ResearchItem");
			ResearchItemConst = TRI.getConstructor(String.class, String.class, AspectList.class, int.class, int.class, int.class,
					ResourceLocation.class);
			ResearchItemStConst = TRI.getConstructor(String.class, String.class, AspectList.class, int.class, int.class, int.class,
					ItemStack.class);
			for(Method method : TRI.getMethods()){
				if(method.getName().equalsIgnoreCase("setPages")){setPages = method;
				}else if(method.getName().equalsIgnoreCase("setAutoUnlock")){setAutoUnlock = method;
				}else if(method.getName().equalsIgnoreCase("setRound")){setRound = method;
				}else if(method.getName().equalsIgnoreCase("setStub")){setStub = method;
				}else if(method.getName().equalsIgnoreCase("registerResearchItem")){registerResearchItem = method;
				}else if(method.getName().equalsIgnoreCase("setParents")){setParents = method;
				}else if(method.getName().equalsIgnoreCase("setSecondary")){setSecondary = method;
				}else if(method.getName().equalsIgnoreCase("setSpecial")){setSpecial = method;
				}else if(method.getName().equalsIgnoreCase("setHidden")){setHidden = method;
				}else if(method.getName().equalsIgnoreCase("setItemTriggers")){setItemTriggers = method;
				}
			}

			CrucibleRecipe = Class.forName("thaumcraft.api.crafting.CrucibleRecipe");
			ArcaneRecipe = Class.forName("thaumcraft.api.crafting.IArcaneRecipe");
			InfusionRecipe = Class.forName("thaumcraft.api.crafting.InfusionRecipe");

			ResearchPage = Class.forName("thaumcraft.api.research.ResearchPage");
			ResearchPageArr = Class.forName("[Lthaumcraft.api.research.ResearchPage;");

			ResearchPageString = ResearchPage.getConstructor(String.class);
			ResearchPageCrucible = ResearchPage.getConstructor(CrucibleRecipe);
			ResearchPageIRecipe = ResearchPage.getConstructor(IRecipe.class);
			ResearchPageIArcRecipe = ResearchPage.getConstructor(ArcaneRecipe);
			ResearchPageIInfRecipe = ResearchPage.getConstructor(InfusionRecipe);



			Class UtilsFX = Class.forName("thaumcraft.client.lib.UtilsFX");

			for(Method method : UtilsFX.getMethods()){
				if(method.getName().equalsIgnoreCase("renderQuadCenteredFromTexture")){renderQuadCenteredFromTexture = method;
				}else if(method.getName().equalsIgnoreCase("drawTag")){drawTag = method;
				}
			}

			Class Conf = Class.forName("thaumcraft.common.config.Config");
			crooked = (boolean)Conf.getField("crooked").getBoolean(Conf);
			Class BlockJar = Class.forName("thaumcraft.common.blocks.BlockJar");
			iconLiquid = (Icon)BlockJar.getField("iconLiquid").get(Block.blocksList[(int) Conf.getField("blockJarId").getInt(Conf)]);

			Class JR = Class.forName("thaumcraft.client.renderers.tile.TileJarRenderer");
			for(Method method : JR.getMethods()){
				if(method.getName().equalsIgnoreCase("renderLiquid")){renderLiquid = method;
				//}else if(method.getName().equalsIgnoreCase("drawTag")){drawTag = method;
				}
			}
			JarRenderConst = JR.getConstructor();



			System.out.println("Technomancy: Thaumcraft Module Activated");
		}catch(Exception e){th = false;System.out.println("Technomancy: Failed to load Thaumcraft");}
	}

	public static void wispFX3(World worldObj, double posX, double posY, double posZ, double posX2, double posY2, double posZ2,
			float size, int type, boolean shrink, float gravity) {
		try{
			Class TH = Class.forName("thaumcraft.common.Thaumcraft");
			wispFX3.invoke(TH.getField("proxy").get(TH), worldObj, posX, posY, posZ, posX2, posY2, posZ2, size, type, shrink, gravity);
		}catch(Exception e){e.printStackTrace();}
	}

	public static void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		int md = world.getBlockMetadata(x, y, z);
		TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
		if (!(tileEntity instanceof IInventory)) {
			return;
		}
		IInventory inventory = (IInventory)tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++){
			ItemStack item = inventory.getStackInSlot(i);

			if ((item != null) && (item.stackSize > 0)) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;

				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(),
						item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound((NBTTagCompound)item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = (rand.nextGaussian() * factor);
				entityItem.motionY = (rand.nextGaussian() * factor + 0.2000000029802322D);
				entityItem.motionZ = (rand.nextGaussian() * factor);
				world.spawnEntityInWorld(entityItem);
				inventory.setInventorySlotContents(i, null);
			}
		}
	}

	public static TileEntity getConnectableTile(World world, int x, int y, int z, ForgeDirection face){
		TileEntity te = world.getBlockTileEntity(x + face.offsetX, y + face.offsetY, z + face.offsetZ);
		if (((te instanceof IEssentiaTransport)) && (((IEssentiaTransport)te).isConnectable(face.getOpposite()))) {
			return te;
		}
		return null;
	}

	public static AspectList getObjectAspects(ItemStack is) {
		AspectList ot = null;
		try {
			if (getObjectTags == null) {
				Class fake = Class.forName("thaumcraft.common.lib.ThaumcraftCraftingManager");
				getObjectTags = fake.getMethod("getObjectTags", new Class[] { ItemStack.class });
			}
			ot = (AspectList)getObjectTags.invoke(null, new Object[] { is });
		} catch (Exception ex) {
			cpw.mods.fml.common.FMLLog.warning("[Thaumcraft API] Could not invoke thaumcraft.common.lib.ThaumcraftCraftingManager method getObjectTags", new Object[0]);
		}
		return ot;
	}
}
