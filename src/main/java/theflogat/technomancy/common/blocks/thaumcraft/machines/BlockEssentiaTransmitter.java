package theflogat.technomancy.common.blocks.thaumcraft.machines;

import java.util.ArrayList;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.IEssentiaContainerItem;
import theflogat.technomancy.common.blocks.base.BlockCoilTransmitter;
import theflogat.technomancy.common.items.technom.ItemBoost;
import theflogat.technomancy.common.items.technom.ItemCoilCoupler;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileEssentiaTransmitter;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.Thaumcraft;

public class BlockEssentiaTransmitter extends BlockCoilTransmitter {

	public BlockEssentiaTransmitter() {
		super();
		setBlockName(Ref.MOD_PREFIX + Names.essentiaTransmitter);
	}
	
	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileEssentiaTransmitter();
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem();
		if(stack!=null && (stack.getItem() instanceof ItemCoilCoupler || stack.getItem() instanceof ItemBoost)){
			return false;
		}
		if(super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ)) {
			return true;
		}
		TileEssentiaTransmitter tile = getTE(w, x, y, z);
		if(tile!=null) {
			if(player.isSneaking()) {
				if (tile.aspectFilter != null ) {
					if (!w.isRemote) {
						tile.aspectFilter = null;
						if (!player.inventory.addItemStackToInventory(new ItemStack(Thaumcraft.itemResource, 1, 13))) {
							ForgeDirection fd = ForgeDirection.getOrientation(ForgeDirection.OPPOSITES[tile.facing]);
							w.spawnEntityInWorld(new EntityItem(w, x + 0.5F + fd.offsetX / 3.0F, y + 0.5F, z + 0.5F + fd.offsetZ /
									3.0F, new ItemStack(Thaumcraft.itemResource, 1, 13)));
						}
						w.markBlockForUpdate(x, y, z);
					}else{
						w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
					}
					return true;
				}
			} else if(stack != null && tile.aspectFilter == null) {
				if (stack.getItemDamage() == 13 && stack.getItem() == Thaumcraft.itemResource && stack.getItem() instanceof IEssentiaContainerItem) {
					if (((IEssentiaContainerItem)stack.getItem()).getAspects(stack) != null && tile.aspectFilter == null) {
						if(!w.isRemote) {
							tile.aspectFilter = ((IEssentiaContainerItem)stack.getItem()).getAspects(stack).getAspects()[0];
							stack.stackSize -= 1;
							w.markBlockForUpdate(x, y, z);
						} else {
							w.playSound(x + 0.5F, y + 0.5F, z + 0.5F, "thaumcraft:page", 1.0F, 1.0F, false);
						}
						return true;
					}				
				}
			}
		}
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idEssentiaTransmitter;
	}
	
	@Override
	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
		super.getNBTInfo(comp, l, meta);
		Aspect filter = Aspect.getAspect(comp.getString("AspectFilter"));
		if(filter!=null){
			l.add("Filter" + filter.getChatcolor() + filter.getName());
		}
	}
	
	private static TileEssentiaTransmitter getTE(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		return tile instanceof TileEssentiaTransmitter ? (TileEssentiaTransmitter)tile : null;
	}
}
