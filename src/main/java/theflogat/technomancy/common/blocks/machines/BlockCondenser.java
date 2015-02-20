package theflogat.technomancy.common.blocks.machines;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.CoFH;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCondenser extends BlockBase {

	public BlockCondenser() {
		setBlockName(Ref.MOD_PREFIX + Names.condenserBlock);
	}
	
	@Override
	public boolean onBlockActivated(World w, int x,	int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		return true;
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)    {
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.condenserBlock));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		try{
			if(side == 0) {
				return (IIcon) CoFH.getIcon.invoke(null, "MachineBottom");
			}
			if(side == 1) {
				return (IIcon) CoFH.getIcon.invoke(null, "MachineTop");
			}
			if((meta == 0 && side == 3) || (meta == 1 && side == 4) || (meta == 2 && side == 2) || (meta == 3 && side == 5)) {
				return blockIcon;
			}
			return (IIcon) CoFH.getIcon.invoke(null, "MachineSide");
		}catch(Exception e){e.printStackTrace();}
		
		return blockIcon;
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCondenser();
	}
}