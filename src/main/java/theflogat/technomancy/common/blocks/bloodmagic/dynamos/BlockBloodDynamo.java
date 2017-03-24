package theflogat.technomancy.common.blocks.bloodmagic.dynamos;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockDynamoBase;
import theflogat.technomancy.common.tiles.bloodmagic.dynamos.TileBloodDynamo;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;
import theflogat.technomancy.lib.compat.BloodMagic;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBloodDynamo extends BlockDynamoBase {

    public BlockBloodDynamo() {
        super();
        setBlockName(Reference.getId(Names.BLOODDYNAMO));
    }

    @Override
    public boolean onBlockActivated(final World world, final int x, final int y, final int z, final EntityPlayer player, final int side, final float vecX, final float vecY, final float vecZ) {
        if (player.getHeldItem() != null) {
            final TileBloodDynamo tile = (TileBloodDynamo) world.getTileEntity(x, y, z);
            if (player.getHeldItem().getItem() == BloodMagic.divinationSigil) {
                if (!world.isRemote) {
                    player.addChatComponentMessage(new ChatComponentText("Energy: " + tile.getEnergyStored(null) + "/" + tile.getMaxEnergyStored(null)));
                    player.addChatComponentMessage(new ChatComponentText("Blood: " + tile.liquid + "/" + TileBloodDynamo.CAPACITY));
                }
                return true;
            } else if (player.getHeldItem().getItem() == BloodMagic.bucketLife && tile.emptyBucket()) {
                player.inventory.mainInventory[player.inventory.currentItem] = new ItemStack(Items.bucket);
                return true;
            }
        }
        return super.onBlockActivated(world, x, y, z, player, side, vecX, vecY, vecZ);
    }

    @Override
    public TileEntity createNewTileEntity(final World w, final int meta) {
        return new TileBloodDynamo();
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(final IIconRegister icon) {
        blockIcon = icon.registerIcon(Reference.getAsset(Names.BLOODDYNAMO));
    }

    @Override
    public int getRenderType() {
        return RenderIds.idBloodDynamo;
    }
}