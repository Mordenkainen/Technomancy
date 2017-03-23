package theflogat.technomancy.common.items.thaumcraft;

import ic2.api.tile.IWrenchable;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import powercrystals.minefactoryreloaded.api.IMFRHammer;
import cpw.mods.fml.common.Loader;
import cpw.mods.fml.common.Optional;
import cpw.mods.fml.common.eventhandler.Event.Result;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import mods.railcraft.api.core.items.IToolCrowbar;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockLever;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagByte;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ForgeDirection;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent.Action;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.wands.WandCap;
import thaumcraft.api.wands.WandRod;
import thaumcraft.common.items.wands.ItemWandCasting;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.util.helpers.BlockHelper;
import appeng.api.implementations.items.IAEWrench;
import buildcraft.api.tools.IToolWrench;
import cofh.api.block.IDismantleable;
import cofh.api.energy.IEnergyContainerItem;
import cofh.api.item.IToolHammer;
import crazypants.enderio.api.tool.IConduitControl;
import crazypants.enderio.api.tool.ITool;

@Optional.InterfaceList({
    @Optional.Interface(iface = "crazypants.enderio.api.tool.IConduitControl", modid = "EnderIO"),
    @Optional.Interface(iface = "crazypants.enderio.api.tool.ITool", modid = "EnderIO"),
    @Optional.Interface(iface = "cofh.api.item.IToolHammer", modid = "CoFHCore"),
    @Optional.Interface(iface = "buildcraft.api.tools.IToolWrench", modid = "BuildCraftAPI|Core"),
    @Optional.Interface(iface = "appeng.api.implementations.items.IAEWrench", modid = "appliedenergistics2"),
    @Optional.Interface(iface = "mods.railcraft.api.core.items.IToolCrowbar", modid = "Railcraft"),
    @Optional.Interface(iface = "powercrystals.minefactoryreloaded.api.IMFRHammer", modid = "MineFactoryReloaded")
})
public class ItemTechnoturgeScepter extends ItemWandCasting implements
		IToolCrowbar, IToolHammer,
		IMFRHammer, IToolWrench, ITool, 
		IAEWrench, IConduitControl,
		IEnergyContainerItem {
	
	private final Set<Class<? extends Block>> shiftRotations = new HashSet<Class<? extends Block>>();
	private static final int MAX_ENERGY = 9000000;
	private static final int MAX_VIS = 90000;
	
	public ItemTechnoturgeScepter()
	{
		this.maxStackSize = 1;
		setMaxDamage(0);
		shiftRotations.add(BlockLever.class);
        shiftRotations.add(BlockButton.class);
        shiftRotations.add(BlockChest.class);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item id, CreativeTabs tab, List list){
		ItemStack scepter = new ItemStack(TMItems.itemTechnoturgeScepter, 1);
		scepter.setTagInfo("sceptre", new NBTTagByte((byte)1));
		((ItemWandCasting)scepter.getItem()).setCap(scepter, WandCap.caps.get("thaumium"));
		((ItemWandCasting)scepter.getItem()).setRod(scepter, WandRod.rods.get("technoturge"));
		ItemStack scepterCharged = scepter.copy();
		for(Aspect al : Aspect.getPrimalAspects()) {
			((ItemWandCasting)scepterCharged.getItem()).addVis(scepterCharged, al, 150, true);
		}
		list.add(scepter);
		list.add(scepterCharged);
	}
	
	@Override
    public boolean doesSneakBypassUse(World world, int x, int y, int z, EntityPlayer player) {
        return true;
    }
	
	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
        Block block = world.getBlock(x, y, z);

        if (block == null) {
            return false;
        }

        if (player.isSneaking() != isShiftRotation(block.getClass())) {
            return false;
        }
        
        PlayerInteractEvent e = new PlayerInteractEvent(player, Action.RIGHT_CLICK_BLOCK, x, y, z, side, world);
		if (MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Result.DENY || e.useBlock == Result.DENY || e.useItem == Result.DENY) {
			return false;
		}
		
		if (player.isSneaking() && block instanceof IDismantleable && ((IDismantleable) block).canDismantle(player, world, x, y, z)) {
			if (!world.isRemote) {
				((IDismantleable) block).dismantleBlock(player, world, x, y, z, false);
			}
			player.swingItem();
			return !world.isRemote;
		} if (BlockHelper.canRotate(block)) {
			if (player.isSneaking()) {
				world.setBlockMetadataWithNotify(x, y, z, BlockHelper.rotateVanillaBlockAlt(world, block, x, y, z), 3);
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, block.stepSound.getBreakSound(), 1.0F, 0.6F);
			} else {
				world.setBlockMetadataWithNotify(x, y, z, BlockHelper.rotateVanillaBlock(world, block, x, y, z), 3);
				world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, block.stepSound.getBreakSound(), 1.0F, 0.8F);
			}
			return !world.isRemote;
		} else if (!player.isSneaking() && block.rotateBlock(world, x, y, z, ForgeDirection.getOrientation(side))) {
			return !world.isRemote;
		}
		TileEntity tile = world.getTileEntity(x, y, z);

		if (Loader.isModLoaded("IC2")) {
    		if (tile instanceof IWrenchable) {
    			IWrenchable wrenchable = (IWrenchable) tile;
    
    			if (player.isSneaking()) {
    				side = BlockHelper.SIDE_OPPOSITE[side];
    			}
    			if (wrenchable.wrenchCanSetFacing(player, side)) {
    				if (!world.isRemote) {
    					wrenchable.setFacing((short) side);
    				}
    			} else if (wrenchable.wrenchCanRemove(player)) {
    				ItemStack dropBlock = wrenchable.getWrenchDrop(player);
    
    				if (dropBlock != null) {
    					world.setBlockToAir(x, y, z);
    					if (!world.isRemote) {
    						List<ItemStack> drops = block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
    
    						if (drops.isEmpty()) {
    							drops.add(dropBlock);
    						} else {
    							drops.set(0, dropBlock);
    						}
    						for (ItemStack drop : drops) {
    							float f = 0.7F;
    							double x2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    							double y2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    							double z2 = world.rand.nextFloat() * f + (1.0F - f) * 0.5D;
    							EntityItem entity = new EntityItem(world, x + x2, y + y2, z + z2, drop);
    							entity.delayBeforeCanPickup = 10;
    							world.spawnEntityInWorld(entity);
    						}
    					}
    				}
    			}
    			return !world.isRemote;
			}
		}
		
        return super.onItemUseFirst(stack, player, world, x, y, z, side, hitX, hitY, hitZ);
    }
	
	private boolean isShiftRotation(Class<? extends Block> cls) {
        for (Class<? extends Block> shift : shiftRotations) {
            if (shift.isAssignableFrom(cls))
                return true;
        }
        return false;
    }
	
	@Optional.Method(modid = "EnderIO")
	@Override
	public boolean shouldHideFacades(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Optional.Method(modid = "EnderIO")
	@Override
	public boolean showOverlay(ItemStack stack, EntityPlayer player) {
		return true;
	}

	@Optional.Method(modid = "appliedenergistics2")
	@Override
	public boolean canWrench(ItemStack wrench, EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Optional.Method(modid = "EnderIO")
	@Override
	public boolean canUse(ItemStack stack, EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Optional.Method(modid = "EnderIO")
	@Override
	public void used(ItemStack stack, EntityPlayer player, int x, int y, int z) {}

	@Optional.Method(modid = "BuildCraftAPI|Core")
	@Override
	public boolean canWrench(EntityPlayer player, int x, int y, int z) {
		return true;
	}

	@Optional.Method(modid = "BuildCraftAPI|Core")
	@Override
	public void wrenchUsed(EntityPlayer player, int x, int y, int z) {
		player.swingItem();
	}

	@Optional.Method(modid = "CoFHCore")
	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, int x, int y, int z) {
		return true;
	}

	@Optional.Method(modid = "CoFHCore")
	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, int x, int y, int z) {}

	@Optional.Method(modid = "Railcraft")
	@Override
	public boolean canWhack(EntityPlayer player, ItemStack crowbar, int x, int y, int z) {
		return true;
	}

	@Optional.Method(modid = "Railcraft")
	@Override
	public void onWhack(EntityPlayer player, ItemStack crowbar, int x, int y, int z) {
		player.swingItem();
	}

	@Optional.Method(modid = "Railcraft")
	@Override
    public boolean canLink(EntityPlayer player, ItemStack crowbar, EntityMinecart cart) {
        return player.isSneaking();
    }

	@Optional.Method(modid = "Railcraft")
	@Override
    public void onLink(EntityPlayer player, ItemStack crowbar, EntityMinecart cart) {
        player.swingItem();
    }
	
	@Optional.Method(modid = "Railcraft")
	@Override
	public boolean canBoost(EntityPlayer player, ItemStack crowbar, EntityMinecart cart) {
		return !player.isSneaking();
	}

	@Optional.Method(modid = "Railcraft")
	@Override
	public void onBoost(EntityPlayer player, ItemStack crowbar, EntityMinecart cart) {
		player.swingItem();
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {
		if(this.getAllVis(container).visSize() < MAX_VIS) {
			int tempEnergy = maxReceive;
			if(container.hasTagCompound()) {
				tempEnergy += container.getTagCompound().getInteger("tempEnergy");
				container.getTagCompound().removeTag("tempEnergy");
			}
			if(tempEnergy < 10000) {
				if(!simulate) {
					if(!container.hasTagCompound()) {
						container.setTagCompound(new NBTTagCompound());
					}
					container.getTagCompound().setInteger("tempEnergy", tempEnergy);
				}
				return maxReceive;
			}
			int availEnergy = tempEnergy;
			while(availEnergy >= 10000 && this.getAllVis(container).visSize() < MAX_VIS) {
				for(Aspect al : Aspect.getPrimalAspects()) {
					if(this.getVis(container, al) < this.getMaxVis(container)) {
						this.addVis(container, al, 1, true);
						availEnergy -= 10000;
						if(availEnergy < 10000) {
							break;
						}
					}
				}
			}
			return maxReceive - availEnergy;
		}
		return 0;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {
		return 0;
	}

	@Override
	public int getEnergyStored(ItemStack container) {
		int currentVis = this.getAllVis(container).visSize();
		return currentVis * 100;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {
		return MAX_ENERGY;
	}
}
