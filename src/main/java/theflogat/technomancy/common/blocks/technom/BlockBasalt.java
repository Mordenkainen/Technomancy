package theflogat.technomancy.common.blocks.technom;

import net.minecraft.client.renderer.texture.IIconRegister;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Reference;

public class BlockBasalt extends BlockBase {

    public BlockBasalt() {
        super();
        setBlockName(Reference.getId(Names.BASALT));
    }

    @Override
    public void registerBlockIcons(final IIconRegister reg) {
        blockIcon = reg.registerIcon(Reference.getAsset(Names.BASALT));
    }

}
