package theflogat.technomancy.common.blocks.technom;

import net.minecraft.client.renderer.texture.IIconRegister;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;

public class BlockBasalt extends BlockBase {

    public BlockBasalt() {
        setBlockName(Ref.getId(Names.basalt));
    }

    @Override
    public void registerBlockIcons(IIconRegister reg) {
        blockIcon = reg.registerIcon(Ref.getAsset(Names.basalt));
    }

}
