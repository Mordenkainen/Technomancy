package theflogat.technomancy.common.blocks.storage;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class BlockEssentiaContainer extends BlockBase {
	
	public static BlockContainer instance;

	public BlockEssentiaContainer() {
		this.setHardness(1F);
		this.setBlockName(Ref.MOD_PREFIX + Names.essentiaContainer);
		this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		int face = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		TileEntity tile = world.getTileEntity(x, y, z);
		if ((tile instanceof TileEssentiaContainer)) {
			if (face == 0) {
				((TileEssentiaContainer)tile).facing = 2;
			}
			if (face == 1) {
				((TileEssentiaContainer)tile).facing = 5;
			}
			if (face == 2) {
				((TileEssentiaContainer)tile).facing = 3;
			}
			if (face == 3) {
				((TileEssentiaContainer)tile).facing = 4;
			}
		}
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ)  {
		if (world.getTileEntity(x, y, z) instanceof TileEssentiaContainer ) {
			TileEssentiaContainer container = (TileEssentiaContainer)world.getTileEntity(x, y, z);	  
			ItemStack item = player.getHeldItem();		
			//Removes labels
			if (container.aspectFilter != null && item == null  && player.isSneaking() && side == container.facing) {
				container.aspectFilter = null;
				if (world.isRemote) {
			        world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
			    }
			    ForgeDirection fd = ForgeDirection.getOrientation(side);
			    if (!player.inventory.addItemStackToInventory(new ItemStack(Thaumcraft.itemResource, 1, 13))) {	
			    	world.spawnEntityInWorld(new EntityItem(world, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ /
			    			3.0F, new ItemStack(Thaumcraft.itemResource, 1, 13)));
			    }			    
				return true;
			}
			//Empties Jars
			if ((player.isSneaking()) && (container.amount >= 0) && item == null && container.aspectFilter == null) {
				container.amount = 0;
				if (world.isRemote) {
					world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:jar", 1.0F, 1.0F, false);
				}				
				return true;
			}
			if(item != null) {
				//Adds labels
				if (container.aspectFilter == null && item.getItemDamage() == 13 && item.getItem() == Thaumcraft.itemResource &&
						container.aspect != null) {
					if(((IEssentiaContainerItem)item.getItem()).getAspects(item) != null) {
						container.aspectFilter = ((IEssentiaContainerItem)item.getItem()).getAspects(item).getAspects()[0];
						item.stackSize -= 1;
						if (world.isRemote) {
							world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
						}
						player.inventoryContainer.detectAndSendChanges();
					}else{
						container.aspectFilter = container.aspect;
						item.stackSize -= 1;
						if (world.isRemote) {
							world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
						}
						player.inventoryContainer.detectAndSendChanges();
					}
					onBlockPlacedBy(world, x, y, z, player, null);
					return true;
				}				
				//Adds Essentia from Phials
				if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 1 && container.amount <= (container.maxAmount - 8)) {
						if(container.addToContainer(((IEssentiaContainerItem)player.getHeldItem().getItem())
								.getAspects(player.getHeldItem()).getAspects()[0], 8) == 0) {
							item.stackSize -= 1;
							ItemStack phial = new ItemStack(Thaumcraft.itemEssence, 1, 0);
							if (!player.inventory.addItemStackToInventory(phial)) {		       
								world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, phial));
							}
							world.playSoundAtEntity(player, "liquid.swim", 0.25F, 1.0F);
							player.inventoryContainer.detectAndSendChanges();
							return true;	
						}
				}				
				//Adds Essentia to Phials
				if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 0 && container.aspect != null) {
					Aspect asp = Aspect.getAspect(container.aspect.getTag());
					if (container.takeFromContainer(container.aspect, 8) == true) {
						item.stackSize -= 1;
						world.playSoundAtEntity(player, "liquid.swim", 0.25F, 1.0F);						
						ItemStack phial = new ItemStack(Thaumcraft.itemEssence, 1, 1);
						setAspects(phial, new AspectList().add(asp, 8));
						if (!player.inventory.addItemStackToInventory(phial)) {
							world.spawnEntityInWorld(new EntityItem(world,  x + 0.5F, y + 0.5F, z + 0.5F, phial));
						}	
						player.inventoryContainer.detectAndSendChanges();
						return true;
					}					
				}			
			}			 
		}
		return true;
	}	

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaContainer();
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idEssentiaContainer;
	}
	
	public void setAspects(ItemStack itemstack, AspectList aspects){
	    if (!itemstack.hasTagCompound()) {
	      itemstack.setTagCompound(new NBTTagCompound());
	   }
	    aspects.writeToNBT(itemstack.getTagCompound());
	}	
	
	@SideOnly(Side.CLIENT)
	public static IIcon iconLiquid;
	
	@SideOnly(Side.CLIENT)
	public static IIcon iconJar;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		iconJar = icon.registerIcon(Ref.MODEL_PREFIX + Names.essentiaContainer);
		iconLiquid = icon.registerIcon(Ref.TEXTURE_PREFIX + "animatedglow");
		blockIcon = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.essentiaContainer);
	}
}
