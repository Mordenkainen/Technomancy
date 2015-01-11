package theflogat.technomancy.common.blocks.dynamos;

import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.items.api.ToolWrench;
import theflogat.technomancy.common.tiles.base.TileDynamoBase;
import theflogat.technomancy.common.tiles.dynamos.TileBloodDynamo;
import theflogat.technomancy.handlers.compat.BloodMagic;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloodDynamo extends BlockBase {

	public BlockBloodDynamo() {
		setBlockName(Ref.MOD_PREFIX + Names.bloodDynamo);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBloodDynamo();
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		this.icon = icon.registerIcon(Ref.getAsset(Names.bloodDynamo));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float vecX, float vecY, float vecZ) {
		if (!world.isRemote) {
			if (player.getHeldItem() != null) { 
				TileEntity entity = world.getTileEntity(x, y, z);
				if (entity instanceof TileBloodDynamo) {
					if (ToolWrench.isWrench(player.getHeldItem())) {
						((TileBloodDynamo)entity).rotateBlock();
					}else if(player.getHeldItem().getItem()==BloodMagic.divinationSigil){
						player.addChatComponentMessage(new ChatComponentText("Energy: " + ((TileBloodDynamo)entity).getEnergyStored(null) + "/" +
								((TileBloodDynamo)entity).getMaxEnergyStored(null)));
						player.addChatComponentMessage(new ChatComponentText("Blood: " + ((TileBloodDynamo)entity).liquid + "/" + TileBloodDynamo.capacity));
					}else if(player.getHeldItem().getItem()==BloodMagic.bucketLife){
						if(((TileBloodDynamo)entity).emptyBucket())
							player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.bucket);
					}
				}
			}else{
				return true;
			}
		}
		return false;
	}

	@Override   
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		super.onBlockPlacedBy(world, x, y, z, entity, stack);
	}  

	@Override
	public boolean isOpaqueCube(){
		return false;
	}

	@Override
	public boolean renderAsNormalBlock() {
		return false;
	}

	@Override
	public int getRenderType() {
		return RenderIds.idBloodDynamo;
	}
}