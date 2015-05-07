package theflogat.technomancy.common.blocks.thaumcraft.machines;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileCondenser;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCondenser extends BlockContainerAdvanced {

	@SideOnly(Side.CLIENT)
	IIcon[] icons;
	
	public BlockCondenser() {
		setBlockName(Ref.MOD_PREFIX + Names.condenserBlock);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)    {
		int rotation = MathHelper.floor_double(entity.rotationYaw * 4.0F / 360.0F + 2.5D) & 3;
		world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		icons = new IIcon[4];
		icons[0] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Front"));
		icons[1] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Side"));
		icons[2] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Top"));
		icons[3] = icon.registerIcon(Ref.getAsset(Names.condenserBlock + "Bottom"));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 0) {
			return icons[2];
		}
		if(side == 1) {
			return icons[3];
		}
		if((meta == 0 && side == 3) || (meta == 1 && side == 4) || (meta == 2 && side == 2) || (meta == 3 && side == 5)) {
			return icons[0];
		}
		return icons[1];
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileCondenser();
	}
}