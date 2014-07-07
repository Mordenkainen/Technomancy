package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.IAspectSource;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.blocks.machines.tiles.TileTeslaCoil;
import democretes.compat.Thaumcraft;
import democretes.lib.Names;
import democretes.lib.Ref;

public class ItemMaterial extends ItemBase {

	public ItemMaterial(int id) {
		super(id);
		setMaxStackSize(64);
		setHasSubtypes(true);
	}


	public Icon[] itemIcon = new Icon[5];

	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister icon) {
		itemIcon[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + "neutronizedMetal");
		itemIcon[1] = icon.registerIcon(Ref.TEXTURE_PREFIX + "enchantedCoil");
		itemIcon[2] = icon.registerIcon(Ref.TEXTURE_PREFIX + "neutronizedGear");
		itemIcon[3] = icon.registerIcon(Ref.TEXTURE_PREFIX + "penCore");
		itemIcon[4] = icon.registerIcon(Ref.TEXTURE_PREFIX + "coilCoupler");
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int par) {
		return this.itemIcon[par];
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {
		return Ref.MOD_PREFIX + Names.itemMaterial + "." + stack.getItemDamage();
	}

	@SideOnly(Side.CLIENT)
	public void getSubItems(int id, CreativeTabs tab, List list) {
		for (int i = 0; i < itemIcon.length; i++) {
			ItemStack stack  = new ItemStack(id, 1, i);
			list.add(stack);
		}
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if(stack.stackTagCompound==null && stack.getItemDamage() == 4){
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean("ent", false);
			stack.stackTagCompound.setInteger("x", 0);
			stack.stackTagCompound.setInteger("y", 0);
			stack.stackTagCompound.setInteger("z", 0);
			stack.stackTagCompound.setInteger("dimId", 0);
		}

		if(!world.isRemote) {
			TileEntity tile = world.getBlockTileEntity(x, y, z);
			if(tile != null && stack.getItemDamage() == 4) {			
				if(tile instanceof IAspectSource && !Thaumcraft.TileMirrorEssentia.isInstance(tile) && !(tile instanceof TileTeslaCoil)) {
					if(stack.stackTagCompound.getBoolean("ent")) {
						if(tile.getWorldObj().provider.dimensionId == stack.stackTagCompound.getInteger("dimId")){
							if(!areCoordsEqual(stack.stackTagCompound, x, y, z)) {
								int[] i = retrievePos(stack.stackTagCompound);
								((TileTeslaCoil)world.getBlockTileEntity(i[0], i[1], i[2])).sources.add(new ChunkCoordinates(x, y, z));
								player.sendChatToPlayer(ChatMessageComponent.createFromText("Linked"));
								stack.stackTagCompound.setBoolean("ent", false);
								return true;
							}
						}else{
							player.sendChatToPlayer(ChatMessageComponent.createFromText("Cannot create an interdimensional link"));
						}
					}else{
						player.sendChatToPlayer(ChatMessageComponent.createFromText("No valid wireless destination available"));
					}
				}else if(tile instanceof TileTeslaCoil && (!stack.stackTagCompound.getBoolean("ent") || player.isSneaking())) {
					if(player.isSneaking()) {
						((TileTeslaCoil)tile).sources.clear();
						player.sendChatToPlayer(ChatMessageComponent.createFromText("Links Cleared"));
						return false;
					}else{
						player.sendChatToPlayer(ChatMessageComponent.createFromText("Begin Linking"));					
						stack.stackTagCompound.setBoolean("ent", true);
						stack.stackTagCompound.setInteger("x", x);
						stack.stackTagCompound.setInteger("y", y);
						stack.stackTagCompound.setInteger("z", z);
						stack.stackTagCompound.setInteger("dimId", tile.worldObj.provider.dimensionId);
						return true;
					}

				}else{
					player.sendChatToPlayer(ChatMessageComponent.createFromText("Not a valid source"));
				}
			}
		}
		return false;
	}

	private boolean areCoordsEqual(NBTTagCompound comp, int x, int y, int z) {
		int nx = comp.getInteger("x");int ny = comp.getInteger("y");int nz = comp.getInteger("z");

		return nx==x && ny==y && nz==z;
	}

	private int[] retrievePos(NBTTagCompound comp) {
		return new int[]{comp.getInteger("x"),comp.getInteger("y"),comp.getInteger("z")};
	}

}
