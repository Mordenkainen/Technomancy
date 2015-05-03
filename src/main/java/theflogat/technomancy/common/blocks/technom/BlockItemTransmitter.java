package theflogat.technomancy.common.blocks.technom;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.base.TMItems;
import theflogat.technomancy.common.tiles.technom.TileItemTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;
import theflogat.technomancy.util.InvHelper;
import theflogat.technomancy.util.WorldHelper;

public class BlockItemTransmitter extends BlockBase{

	public BlockItemTransmitter() {
		setBlockName(Ref.getId(Names.itemTransmitter));
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileItemTransmitter();
	}
	
	@Override
	public void breakBlock(World w, int x, int y, int z, Block b, int flag) {
		TileItemTransmitter tile = getTE(w, x, y, z);
		if(tile.boost)
			WorldHelper.dropBoost(w, x, y, z);
		super.breakBlock(w, x, y, z, b, flag);
	}

	//FIXME: This may be unstable since this class is a singleton!
	private byte facing;

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack){
		TileItemTransmitter tile = getTE(world, x, y, z);
		if(tile != null) {
			tile.facing = this.facing;
		}
	}

	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta){
		this.facing = (byte) ForgeDirection.OPPOSITES[side];
		return side + meta;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess world, int x, int y, int z) {
		TileItemTransmitter tile = getTE(world, x, y, z);
		if(tile != null) {
			switch(tile.facing) {
			case 0:
				setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
				break;
			case 1:
				setBlockBounds(0.1875F, 0.0F, 0.1875F, 0.8125F, 1.0F, 0.8125F);
				break;
			case 2:
				setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
				break;
			case 3:
				setBlockBounds(0.1875F, 0.1875F, 0.0F, 0.8125F, 0.8125F, 1.0F);
				break;
			case 4:
				setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
				break;
			case 5:
				setBlockBounds(0.0F, 0.1875F, 0.1875F, 1.0F, 0.8125F, 0.8125F);
				break;
			}
		}
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
		return RenderIds.idItemTransmitter;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.essentiaTransmitter));
	}

	private TileItemTransmitter getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if (tile instanceof TileItemTransmitter) {
			return (TileItemTransmitter)tile;
		}
		return null;
	}

}
