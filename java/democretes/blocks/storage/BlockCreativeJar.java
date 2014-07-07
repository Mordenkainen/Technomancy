package democretes.blocks.storage;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.compat.Thaumcraft;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;

public class BlockCreativeJar extends BlockBase {
	
	public BlockCreativeJar(int id) {
		super(id);
		this.setHardness(1F);
		this.setUnlocalizedName(Ref.MOD_PREFIX + Names.creativeJar);
		this.setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 0.75F, 0.8125F);
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		int face = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 0.5D) & 0x3;
		TileEntity tile = world.getBlockTileEntity(x, y, z);
		if ((tile instanceof TileCreativeJar)) {
			if (face == 0) {
				((TileCreativeJar)tile).facing = 2;
			}
			if (face == 1) {
				((TileCreativeJar)tile).facing = 5;
			}
			if (face == 2) {
				((TileCreativeJar)tile).facing = 3;
			}
			if (face == 3) {
				((TileCreativeJar)tile).facing = 4;
			}
		}
	}
	
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ)  {
		if (world.getBlockTileEntity(x, y, z) instanceof TileCreativeJar ) {
			TileCreativeJar container = (TileCreativeJar)world.getBlockTileEntity(x, y, z);	  
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
					}
					container.aspectFilter = container.aspect;
					item.stackSize -= 1;
					if (world.isRemote) {
						world.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}
					player.inventoryContainer.detectAndSendChanges();
					return true;
				}				
				//Adds Essentia from Phials
				if (item.getItem() == Thaumcraft.itemEssence && item.getItemDamage() == 1 && container.amount <= (container.maxAmount - 8)) {
						if(container.addToContainer(((IEssentiaContainerItem)(IEssentiaContainerItem)player.getHeldItem().getItem())
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

	public TileEntity createNewTileEntity(World world) {
		return new TileCreativeJar();
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
		return RenderIds.idCreativeJar;
	}
	
	public void setAspects(ItemStack itemstack, AspectList aspects){
	    if (!itemstack.hasTagCompound()) {
	      itemstack.setTagCompound(new NBTTagCompound("tag"));
	   }
	    aspects.writeToNBT(itemstack.getTagCompound());
	}

	public Icon iconJar;
	public Icon iconLiquid;
	
	public void RegisterIcons(IconRegister icon) {
		iconJar = icon.registerIcon("technom:models/essentiaContainer");
		iconLiquid = icon.registerIcon("technom:animatedglow");
		
	}
	
	public Icon getIcon(int side, int meta) {
		return iconJar;
		
	}
}
