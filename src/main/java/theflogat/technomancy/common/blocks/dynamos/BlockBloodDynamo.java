package theflogat.technomancy.common.blocks.dynamos;

import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.dynamos.TileBloodDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.BloodMagic;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloodDynamo extends BlockDynamoBase {

	public BlockBloodDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.bloodDynamo);
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (player.getHeldItem() != null) {
			TileBloodDynamo tile = (TileBloodDynamo)world.getTileEntity(x, y, z);
			if(player.getHeldItem().getItem()==BloodMagic.divinationSigil){
				if(!world.isRemote) {
					player.addChatComponentMessage(new ChatComponentText("Energy: " + tile.getEnergyStored(null) + "/" + tile.getMaxEnergyStored(null)));
					player.addChatComponentMessage(new ChatComponentText("Blood: " + tile.liquid + "/" + TileBloodDynamo.capacity));
				}
				return true;
			}else if(player.getHeldItem().getItem()==BloodMagic.bucketLife){
				if(tile.emptyBucket()) {
					player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.bucket);
					return true;
				}
			}
		}
		return super.onBlockActivated(world, x, y, z, player, side, vecX, vecY, vecZ);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBloodDynamo();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.bloodDynamo));
	}

	@Override
	public int getRenderType() {
		return RenderIds.idBloodDynamo;
	}
}