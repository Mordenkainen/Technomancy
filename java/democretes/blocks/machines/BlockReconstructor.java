package democretes.blocks.machines;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileReconstructor;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;

public class BlockReconstructor extends BlockBase {

	public BlockReconstructor(int id) {
		super(id);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.reconstructor);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(!world.isRemote) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null) {
				if(tile instanceof TileReconstructor == false) {
					return false;
				}
				TileReconstructor recon = (TileReconstructor)tile;
				if(player.getHeldItem() == null) {
					if(player.isSneaking() && recon.contents[0] != null) {
						if (!player.inventory.addItemStackToInventory(new ItemStack(recon.contents[0].itemID, 1, recon.contents[0].getItemDamage()))) {	
					    	world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, new ItemStack(recon.contents[0].itemID, 1, recon.contents[0].getItemDamage())));
					    }	
						recon.contents[0] = null;						
					}else if(recon.contents[1] != null) {
						if (!player.inventory.addItemStackToInventory(new ItemStack(recon.contents[1].itemID, recon.contents[1].stackSize, recon.contents[1].getItemDamage()))) {	
					    	world.spawnEntityInWorld(new EntityItem(world, x + 0.5F, y + 0.5F, z + 0.5F, new ItemStack(recon.contents[1].itemID, recon.contents[1].stackSize, recon.contents[1].getItemDamage())));
					    }	
						recon.contents[1] = null;					
					}
				}else{					
					if(recon.contents[0] == null) {
						recon.contents[0] = player.getHeldItem().copy();
						recon.contents[0].stackSize = 1;
						player.getHeldItem().stackSize -= 1;
					}
				}
			}			
		}
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileReconstructor();
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
		return RenderIds.idReconstructor;
	}
	
	@SideOnly(Side.CLIENT)
	public Icon iconReconstructor;
	
	@SideOnly(Side.CLIENT)
	public void RegisterIcons(IconRegister icon) {
		this.iconReconstructor = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.condenserBlock);
	}

}
