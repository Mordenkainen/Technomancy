package theflogat.technomancy.common.items.technom;

import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.tiles.base.ICouplable;
import theflogat.technomancy.common.tiles.base.ICouplable.Couple;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class ItemCoilCoupler extends Item {
	
	public ItemCoilCoupler() {
		setUnlocalizedName(Ref.getId(Names.coilCoupler));
		setCreativeTab(Technomancy.tabsTM);
	}

	/*
	@Override
	public void registerIcons(IIconRegister reg) {
		itemIcon = reg.registerIcon(Ref.getAsset(Names.coilCoupler));
	}
	*/

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World w, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		ItemStack stack = player.getHeldItem(hand);
		if(stack.getTagCompound()==null){
			stack.setTagCompound(new NBTTagCompound());
			stack.getTagCompound().setBoolean("ent", false);
			stack.getTagCompound().setString("type", "");
			stack.getTagCompound().setInteger("x", 0);
			stack.getTagCompound().setInteger("y", 0);
			stack.getTagCompound().setInteger("z", 0);
			stack.getTagCompound().setInteger("dimId", 0);
		}

		if(!w.isRemote) {
			TileEntity te = w.getTileEntity(pos);
			if(te != null) {
				if(te instanceof ICouplable){
					if(player.isSneaking()) {
						((ICouplable)te).clear();
						player.sendMessage(new TextComponentString("Links Cleared"));
						return EnumActionResult.FAIL;
					} else {
						player.sendMessage(new TextComponentString("Begin Linking"));
						stack.getTagCompound().setBoolean("ent", true);
						stack.getTagCompound().setString("type", ((ICouplable)te).getType().id);
						stack.getTagCompound().setInteger("x", pos.getX());
						stack.getTagCompound().setInteger("y", pos.getY());
						stack.getTagCompound().setInteger("z", pos.getZ());
						stack.getTagCompound().setInteger("dimId", te.getWorld().provider.getDimension());
						return EnumActionResult.SUCCESS;
					}
				} else {
					ArrayList<String> t = Couple.getType(te);
					if(t.contains(stack.getTagCompound().getString("type"))) {
						if(stack.getTagCompound().getBoolean("ent")) {
							if(te.getWorld().provider.getDimension()==stack.getTagCompound().getInteger("dimId")) {
								if(!areCoordsEqual(stack.getTagCompound(), pos.getX(), pos.getY(), pos.getZ())) {
									int[] i = retrievePos(stack.getTagCompound());
									if(w.getTileEntity(new BlockPos(i[0], i[1], i[2])) instanceof ICouplable){
										((ICouplable)w.getTileEntity(new BlockPos(i[0], i[1], i[2]))).addPos(pos);
										player.sendMessage(new TextComponentString("Linked"));
										return EnumActionResult.SUCCESS;
									}
									player.sendMessage(new TextComponentString("Core got removed"));
									return EnumActionResult.FAIL;
								}
							} else {
								player.sendMessage(new TextComponentString("Cannot create an interdimensional link"));
							}
						} else {
							player.sendMessage(new TextComponentString("No source to link from"));
						}
					}
				}
			}
		}
		return EnumActionResult.FAIL;
	}
	
	private static boolean areCoordsEqual(NBTTagCompound comp, int x, int y, int z) {
		return comp.getInteger("x")==x && comp.getInteger("y")==y && comp.getInteger("z")==z;
	}

	private static int[] retrievePos(NBTTagCompound comp) {
		return new int[]{comp.getInteger("x"),comp.getInteger("y"),comp.getInteger("z")};
	}

}