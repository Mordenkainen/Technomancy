package theflogat.technomancy.common.blocks.botania;

import java.awt.Color;
import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.BlockFluidBase;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import vazkii.botania.common.Botania;
import net.minecraft.block.material.Material;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidClassic;

public class BlockManaFluid extends BlockFluidClassic {

    public BlockManaFluid(Fluid fluid, Material material) {
        super(fluid, material);
        this.setRegistryName(fluid.getName());
        this.setUnlocalizedName(fluidName);
    }

    @SideOnly(Side.CLIENT)
    public void regFluid() {
        ModelLoader.setCustomStateMapper(this, new StateMap.Builder().ignore(BlockFluidBase.LEVEL).build());
    }

    @Override
    public boolean canDisplace(IBlockAccess world, BlockPos pos) {
        if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
        return super.canDisplace(world, pos);
    }

    @Override
    public boolean displaceIfPossible(World world, BlockPos pos) {
        if (world.getBlockState(pos).getMaterial().isLiquid()) return false;
        return super.displaceIfPossible(world, pos);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void randomDisplayTick(IBlockState state, World world, BlockPos pos, Random r) {
        Color color = new Color(0x00C6FF);
        if (Math.random() > 0.5)
            Botania.proxy.wispFX(pos.getX() + 0.3 + Math.random() * 0.5, pos.getY() + 0.6 + Math.random() * 0.25, pos.getZ() + Math.random(), color.getRed() / 255F, color.getGreen() / 255F, color.getBlue() / 255F, (float) Math.random() / 3F, (float) -Math.random() / 25F, 2F);
    }
}
