package democretes.blocks.machines;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileBloodFabricator;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;

public class BlockBloodFabricator extends BlockBase {

	public BlockBloodFabricator(int id) {
		super(id);
		setUnlocalizedName(Ref.MOD_PREFIX + Names.bloodFabricator);
	}
	
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack)    {
        int rotation = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 2.5D) & 3;
        world.setBlockMetadataWithNotify(x, y, z, rotation, 2);
    }
	
	@SideOnly(Side.CLIENT)
	public Icon fabricator;
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister icon) {
		fabricator = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.bloodFabricator);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileBloodFabricator();
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
		return RenderIds.idBloodFabricator;
	}

}
