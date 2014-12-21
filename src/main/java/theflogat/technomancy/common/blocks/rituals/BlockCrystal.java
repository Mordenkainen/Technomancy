package theflogat.technomancy.common.blocks.rituals;

import java.util.List;
import java.util.Random;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrystal extends BlockBase {

	public BlockCrystal() {
		setBlockName(Ref.MOD_PREFIX + Names.crystalBlock);
	}
	
	@Override
    public void onBlockClicked(World world, int x, int y, int z, EntityPlayer player) {
//		if(player != null) {
//			int meta = world.getBlockMetadata(x, y, z);
//			if(meta == 0) {
//				Random rand = new Random();
//				int dim = rand.nextInt(DimensionManager.getIDs().length);
//				if(DimensionManager.isDimensionRegistered(DimensionManager.getIDs()[dim])) {
//					player.travelToDimension(DimensionManager.getIDs()[dim]);
//				}
//			}					
//		}
	}
	
	public IIcon crystalIcon;	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister icon) {
		
	}
	
	@Override
	public void getSubBlocks(Item id, CreativeTabs tab, List list) {
		list.add(new ItemStack(id, 1, 0));
		list.add(new ItemStack(id, 1, 1));
		list.add(new ItemStack(id, 1, 2));
	}


	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return null;
	}

}
