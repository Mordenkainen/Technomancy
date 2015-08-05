package theflogat.technomancy.common.blocks.thaumcraft.storage;

import java.util.ArrayList;

import net.minecraft.block.BlockContainer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.storage.TileEssentiaContainer;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEssentiaContainer extends BlockContainerAdvanced {
	
	public static BlockContainer instance;

	public BlockEssentiaContainer() {
		setHardness(1F);
		setBlockName(Ref.MOD_PREFIX + Names.essentiaContainer);
		setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase entity, ItemStack items){
		super.onBlockPlacedBy(w, x, y, z, entity, items);
		int face = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		TileEntity tile = w.getTileEntity(x, y, z);
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
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)  {
		if (w.getTileEntity(x, y, z) instanceof TileEssentiaContainer ) {
			TileEssentiaContainer container = (TileEssentiaContainer)w.getTileEntity(x, y, z);	  
			ItemStack item = player.getHeldItem();		
			//Removes labels
			if (container.aspectFilter != null && item == null  && player.isSneaking() && side == container.facing) {
				container.aspectFilter = null;
				if (w.isRemote) {
			        w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
			    }
			    ForgeDirection fd = ForgeDirection.getOrientation(side);
			    if (!player.inventory.addItemStackToInventory(new ItemStack(Thaumcraft.itemResource, 1, 13))) {	
			    	w.spawnEntityInWorld(new EntityItem(w, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ /
			    			3.0F, new ItemStack(Thaumcraft.itemResource, 1, 13)));
			    }			    
				return true;
			}
			//Empties Jars
			if ((player.isSneaking()) && (container.amount >= 0) && item == null && container.aspectFilter == null) {
				container.amount = 0;
				if (w.isRemote) {
					w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:jar", 1.0F, 1.0F, false);
				}				
				return true;
			}
			if(item != null) {
				//Adds labels
				if (container.aspectFilter == null && item.getItemDamage() == 13 && item.getItem() == Thaumcraft.itemResource &&
						container.aspect != null) {
					if(((IEssentiaContainerItem)item.getItem()).getAspects(item) != null) {
						container.aspectFilter = ((IEssentiaContainerItem)item.getItem()).getAspects(item).getAspects()[0];
						
					}else{
						container.aspectFilter = container.aspect;
					}
					item.stackSize--;
					if (w.isRemote) {
						w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}
					player.inventoryContainer.detectAndSendChanges();
					onBlockPlacedBy(w, x, y, z, player, null);
					return true;
				}				
				//Adds Essentia from Phials
				if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 1 && container.amount <= (container.maxAmount - 8)) {
						if(container.addToContainer(((IEssentiaContainerItem)player.getHeldItem().getItem())
								.getAspects(player.getHeldItem()).getAspects()[0], 8) == 0) {
							item.stackSize--;
							ItemStack phial = new ItemStack(Thaumcraft.itemEssence, 1, 0);
							if (!player.inventory.addItemStackToInventory(phial)) {		       
								w.spawnEntityInWorld(new EntityItem(w, x + 0.5F, y + 0.5F, z + 0.5F, phial));
							}
							w.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
							player.inventoryContainer.detectAndSendChanges();
							return true;	
						}
				}				
				//Adds Essentia to Phials
				if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 0 && container.aspect != null) {
					Aspect asp = Aspect.getAspect(container.aspect.getTag());
					if (container.takeFromContainer(container.aspect, 8) == true) {
						item.stackSize--;
						w.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);						
						ItemStack phial = new ItemStack(Thaumcraft.itemEssence, 1, 1);
						setAspects(phial, new AspectList().add(asp, 8));
						if (!player.inventory.addItemStackToInventory(phial)) {
							w.spawnEntityInWorld(new EntityItem(w,  x + 0.5F, y + 0.5F, z + 0.5F, phial));
						}	
						player.inventoryContainer.detectAndSendChanges();
						return true;
					}					
				}
				// Fills empty, non-labeled Jars
				if (((item.getItem() == Item.getItemFromBlock(Thaumcraft.blockJar) && item.getItemDamage() == 0)) && container.amount > 0) {
					int amountToAdd = Math.min(64, container.amount);
					AspectList newAspects = new AspectList().add(container.aspect, amountToAdd);
					container.amount -= amountToAdd;
					if(container.amount <= 0) {
						container.aspect = null;
					}
					ItemStack newJar = new ItemStack(Thaumcraft.itemJarFilled, 1);
					((IEssentiaContainerItem)newJar.getItem()).setAspects(newJar, newAspects);
					item.stackSize--;
					if (!player.inventory.addItemStackToInventory(newJar)) {
						w.spawnEntityInWorld(new EntityItem(w,  x + 0.5F, y + 0.5F, z + 0.5F, newJar));
					}	
					player.inventoryContainer.detectAndSendChanges();
					w.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
					return true;
				}
				//Empties Jars
				if (item.getItem() == Thaumcraft.itemJarFilled && container.amount < container.maxAmount && ((IEssentiaContainerItem)item.getItem()).getAspects(item) != null && ((IEssentiaContainerItem)item.getItem()).getAspects(item).visSize() > 0) {
					Aspect targetAspect = container.aspect == null ? container.aspectFilter == null ? ((IEssentiaContainerItem)item.getItem()).getAspects(item).getAspects()[0] : container.aspectFilter : container.aspect;
					if(targetAspect == ((IEssentiaContainerItem)item.getItem()).getAspects(item).getAspects()[0]) {
						int amountToAdd = Math.min(((IEssentiaContainerItem)item.getItem()).getAspects(item).getAmount(container.aspect), container.maxAmount - container.amount);
						container.aspect = targetAspect;
						container.amount += amountToAdd;
						AspectList newAspects = ((IEssentiaContainerItem)item.getItem()).getAspects(item);
						newAspects.remove(targetAspect, amountToAdd);
						((IEssentiaContainerItem)item.getItem()).setAspects(item, newAspects);
						w.playSoundAtEntity(player, "game.neutral.swim", 0.25F, 1.0F);
						if(((IEssentiaContainerItem)item.getItem()).getAspects(item) == null) {
							if(!item.hasTagCompound() || !item.stackTagCompound.hasKey("AspectFilter")) {
								item.stackSize--;
								ItemStack jar = new ItemStack(Item.getItemFromBlock(Thaumcraft.blockJar), 1, 0);
								if (!player.inventory.addItemStackToInventory(jar)) {
									w.spawnEntityInWorld(new EntityItem(w,  x + 0.5F, y + 0.5F, z + 0.5F, jar));
								}	
								player.inventoryContainer.detectAndSendChanges();
							}
						}
						return true;
					}
					return !player.isSneaking();
				}
			}			 
		}
		return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
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
	
	@Override
	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
		super.getNBTInfo(comp, l, meta);
		if(comp.hasKey("AspectFilter")){
			Aspect as = Aspect.getAspect(comp.getString("AspectFilter"));
			l.add("Filter: " + as.getName());
		}
	}
}
