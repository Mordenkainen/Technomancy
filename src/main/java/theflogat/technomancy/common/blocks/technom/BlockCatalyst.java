package theflogat.technomancy.common.blocks.technom;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockContainerAdvanced;
import theflogat.technomancy.common.tiles.technom.TileCatalyst;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.RenderIds;

public class BlockCatalyst extends BlockContainerAdvanced {

    static final int maxSubBlocks = 5;

    @SideOnly(Side.CLIENT)
    public IIcon[] icons;

    public BlockCatalyst() {
        setBlockName(Ref.getId(Names.catalyst));
        setCreativeTab(Technomancy.tabsTM);
    }

    @Override
    public float getBlockHardness(World w, int x, int y, int z) {
        return ((TileCatalyst) w.getTileEntity(x, y, z)).remCount == -1 ? blockHardness : -1;
    }

    @Override
    public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
        super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
        if (player.isSneaking() && w.getTileEntity(x, y, z) instanceof TileCatalyst) {
            TileCatalyst te = (TileCatalyst) w.getTileEntity(x, y, z);
            if (te.remCount == -1) {
                ((TileCatalyst) w.getTileEntity(x, y, z)).activateRitual(player);
                return true;
            }
        }
        return false;
    }

    @Override
    public void onBlockPlacedBy(World w, int x, int y, int z, EntityLivingBase ent, ItemStack items) {
        w.setBlockMetadataWithNotify(x, y, z, items.getItemDamage(), 3);
        if (ent instanceof EntityPlayer && w.getTileEntity(x, y, z) instanceof TileCatalyst) {
            TileCatalyst te = (TileCatalyst) w.getTileEntity(x, y, z);
            te.userName = ((EntityPlayer) ent).getDisplayName();
        }
    }

    @Override
    public int damageDropped(int meta) {
        return meta;
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(Item id, CreativeTabs tab, List l) {
        for (int i = 0; i < maxSubBlocks; i++) {
            l.add(new ItemStack(id, 1, i));
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void registerBlockIcons(IIconRegister icon) {
        icons = new IIcon[maxSubBlocks];
        for (int i = 0; i < icons.length; i++)
            icons[i] = icon.registerIcon(Ref.getAsset(Names.catalyst) + "_" + i);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(int side, int meta) {
        return icons[meta];
    }

    @Override
    public TileEntity createNewTileEntity(World w, int meta) {
        return new TileCatalyst();
    }

    @Override
    public boolean isOpaqueCube() {
        return false;
    }

    @Override
    public int getRenderType() {
        return RenderIds.idCatalyst;
    }
}
