package theflogat.technomancy.common.blocks.technom.existence;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import theflogat.technomancy.common.blocks.base.BlockContainerBase;
import theflogat.technomancy.common.tiles.technom.existence.TileExistencePylon;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.lib.RenderIds;

public class BlockExistencePylon extends BlockContainerBase {

    public BlockExistencePylon() {
        setBlockName(Reference.getId(Names.EXISTENCEPYLON));
        setBlockBounds(1F / 4F, 0, 1F / 4F, 3F / 4F, 1, 3F / 4F);
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = Blocks.cobblestone.getIcon(0, 0);
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent, ItemStack items) {
        w.setBlockMetadataWithNotify(x, y, z, items.getItemDamage(), 3);
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @Override
    public int getRenderType() {
        return RenderIds.idExPylon;
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileExistencePylon();
    }
}