package theflogat.technomancy.common.blocks.thaumcraft.machines;

import java.util.ArrayList;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileBiomeMorpher;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBiomeMorpher extends BlockContainerAdvanced {

	public BlockBiomeMorpher() {
		setBlockName(Ref.MOD_PREFIX + Names.biomeMorpher);
	}

	@Override
	public TileEntity createNewTileEntity(World w, int meta) {
		return new TileBiomeMorpher();
	}
	
	@Override
	public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase player, ItemStack items) {
		super.onBlockPlacedBy(w, x, y, z, player, items);
		w.setBlockMetadataWithNotify(x, y, z, items.getItemDamage(), 2);
	}

	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		if (!w.isRemote) {
			String pre = "Processing: ";
			if(player.isSneaking()) {
				pre = "Set to: ";
				if (w.getBlockMetadata(x, y, z) != 2) {
					w.setBlockMetadataWithNotify(x, y, z, w.getBlockMetadata(x, y, z) + 1, 3);
				} else {
					w.setBlockMetadataWithNotify(x, y, z, 0, 3);
				}
			}
			String text = null;
			switch (w.getBlockMetadata(x, y, z)) {
			case 0:
				text = "Magical Forest";
				break;
			case 1:
				text = "Eerie";
				break;
			case 2:
				text = "Tainted Land";
			}
			if(text != null) {
				player.addChatComponentMessage(new ChatComponentText(pre + text));
			}
		}
		return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
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
		return RenderIds.idBiomeMorpher;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		blockIcon = icon.registerIcon(Ref.getAsset(Names.biomeMorpher));
	}
	
	@Override
	public void getNBTInfo(NBTTagCompound comp, ArrayList<String> l, int meta) {
		super.getNBTInfo(comp, l, meta);
		switch (meta) {
		case 0:
			l.add("Magical Forest"); break;
		case 1:
			l.add("Eerie"); break;
		case 2:
			l.add("Tainted Land");
		}
	}
}
