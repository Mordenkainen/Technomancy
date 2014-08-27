package democretes.items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import thaumcraft.api.aspects.IAspectContainer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.tiles.thaumcraft.machine.TileTeslaCoil;

public class ItemTHMaterial extends ItemBase {

	public ItemTHMaterial(int id) {
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
	
	@Override
	public void addInformation(ItemStack items, EntityPlayer player, List list, boolean moreInfo) {
		if(items.getItemDamage()==4){
			if(items.stackTagCompound==null){
				items.stackTagCompound = new NBTTagCompound();
				items.stackTagCompound.setBoolean("ent", false);
				items.stackTagCompound.setInteger("x", 0);
				items.stackTagCompound.setInteger("y", 0);
				items.stackTagCompound.setInteger("z", 0);
				items.stackTagCompound.setInteger("dimId", 0);
			}
			boolean ent = items.stackTagCompound.getBoolean("ent");
			if(ent){
				int[] i = retrievePos(items.stackTagCompound);
				list.add("Linking to Wireless Essentia coil at:");
				list.add("x: " + i[0] + " y: " + i[1] + " z: " + i[2]);
				list.add("In dimension " + items.stackTagCompound.getInteger("dimId"));
			}else{
				list.add("Right click on a coil to");
				list.add("begin the linking process.");
				list.add("Shift right click on a essentia storing");
				list.add("device to complete the link.");
				list.add("Shift right click on a coil");
				list.add("to remove all its links.");
			}
		}
	}

	@SideOnly(Side.CLIENT)
	public Icon getIconFromDamage(int dmg) {
		return this.itemIcon[dmg];
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
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World w, int x, int y, int z, int side, float hitX, float hitY,float hitZ){
		if(stack.stackTagCompound==null && stack.getItemDamage() == 4){
			stack.stackTagCompound = new NBTTagCompound();
			stack.stackTagCompound.setBoolean("ent", false);
			stack.stackTagCompound.setInteger("x", 0);
			stack.stackTagCompound.setInteger("y", 0);
			stack.stackTagCompound.setInteger("z", 0);
			stack.stackTagCompound.setInteger("dimId", 0);
		}

		if(!w.isRemote) {
			TileEntity tile = w.getBlockTileEntity(x, y, z);
			if(tile != null && stack.getItemDamage() == 4) {			
				if(tile instanceof IAspectContainer && !tile.getClass().getName().equals("TileMirrorEssentia") && !(tile instanceof TileTeslaCoil)) {
					if(stack.stackTagCompound.getBoolean("ent")) {
						if(tile.getWorldObj().provider.dimensionId == stack.stackTagCompound.getInteger("dimId")){
							if(!areCoordsEqual(stack.stackTagCompound, x, y, z)) {
								int[] i = retrievePos(stack.stackTagCompound);
								((TileTeslaCoil)w.getBlockTileEntity(i[0], i[1], i[2])).sources.add(new ChunkCoordinates(x, y, z));
								player.sendChatToPlayer(ChatMessageComponent.createFromText("Linked"));
								stack.stackTagCompound.setBoolean("ent", false);
								return true;
							}
						}else{
							player.sendChatToPlayer(ChatMessageComponent.createFromText("Cannot create an interdimensional link"));
						}
					}else{
						player.sendChatToPlayer(ChatMessageComponent.createFromText("No source to link from."));
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
