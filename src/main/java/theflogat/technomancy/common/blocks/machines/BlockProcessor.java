package theflogat.technomancy.common.blocks.machines;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import theflogat.technomancy.Technomancy;
import theflogat.technomancy.common.blocks.base.BlockBase;
import theflogat.technomancy.common.tiles.TileBOProcessor;
import theflogat.technomancy.common.tiles.base.TileProcessorBase;
import theflogat.technomancy.common.tiles.bm.TileBMProcessor;
import theflogat.technomancy.common.tiles.thaumcraft.machine.TileTCProcessor;
import theflogat.technomancy.lib.Names;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.lib.compat.Botania;
import theflogat.technomancy.util.InvHelper;
import vazkii.botania.api.wand.IWandHUD;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class BlockProcessor extends BlockBase {

	String name;
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		if(player != null) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileTCProcessor) {		
				player.openGui(Technomancy.instance, 0, world, x, y, z);
			}
			if(tile instanceof TileBMProcessor) {
				player.openGui(Technomancy.instance, 1, world, x, y, z);
			}
			if(tile instanceof TileBOProcessor) {
				player.openGui(Technomancy.instance, 2, world, x, y, z);
			}
		}		
		return true;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack stack) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileBMProcessor && entity instanceof EntityPlayer) {
			((TileBMProcessor)tile).owner = ((EntityPlayer)entity).getDisplayName();
		}
	}
	
	@Override
	public String getUnlocalizedName() {
		return "tile." + Ref.MOD_PREFIX + Names.processor + name;
	}
	
	@SideOnly(Side.CLIENT)
	public IIcon[] icons = new IIcon[4];	
	
	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister icon) {
		icons[0] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Side");
		icons[1] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Active");
		icons[2] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Inactive");
		icons[3] = icon.registerIcon(Ref.TEXTURE_PREFIX + Names.processor + name + "Top");
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int side, int meta) {
		if(side == 1) {
			return icons[3];
		}
		if(side > 1) {
			return icons[2];
		}
		return icons[0];
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side) {
		TileEntity tile = access.getTileEntity(x, y, z);
		if(side == 1) {
			return icons[3];
		}
		if(side > 1) {
			if(tile != null && ((TileProcessorBase)tile).isActive) {
				return icons[1];
			}
			return icons[2];
		}		
		return icons[0];		
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random r)	  {
		TileEntity te = world.getTileEntity(x, y, z);
	    if ((te != null) && ((TileProcessorBase)te).isActive)	    {
	    	float f = x + 0.5F;
    		float f1 = y + 0.2F + r.nextFloat() * 5.0F / 16.0F;
    		float f2 = z + 0.5F;
    		float f3 = 0.52F;
    		float f4 = r.nextFloat() * 0.5F - 0.25F;
	    	if(te instanceof TileTCProcessor || te instanceof TileBMProcessor) {	      
	    		world.spawnParticle("smoke", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	    		world.spawnParticle("flame", f - f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	        
	    		world.spawnParticle("smoke", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	    		world.spawnParticle("flame", f + f3, f1, f2 + f4, 0.0D, 0.0D, 0.0D);
	        
	    		world.spawnParticle("smoke", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	    		world.spawnParticle("flame", f + f4, f1, f2 - f3, 0.0D, 0.0D, 0.0D);
	        
	    		world.spawnParticle("smoke", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	    		world.spawnParticle("flame", f + f4, f1, f2 + f3, 0.0D, 0.0D, 0.0D);
	    	}
	    	if(te instanceof TileBOProcessor) {
	    		f += 0.1F;
	    		f1 += 0.1F;
	    		f2 += 0.1F;
		    	Botania.sparkle(world, (double)f - f3, f1, f2 + f4, r);
		    	Botania.sparkle(world, (double)f + f3, f1, f2 + f4, r);
		    	Botania.sparkle(world, (double)f + f4, f1, f2 - f3, r);
		    	Botania.sparkle(world, (double)f + f4, f1, f2 + f3, r);
		    }
	    }
	}
	
	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) {
		TileEntity tile = world.getTileEntity(x, y, z);
		if(tile instanceof TileProcessorBase) {
			if(((TileProcessorBase)tile).isActive) {
				return 12;
			}
		}
		return super.getLightValue(world, x, y, z);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block id, int meta)	  {
	    InvHelper.dropItemsFromTile(world, x, y, z);
	    super.breakBlock(world, x, y, z, id, meta);
	}

	public static class BlockTCProcessor extends BlockProcessor {

		public BlockTCProcessor() {
			this.name = "TC";
		}
		
		@Override
		public TileEntity createNewTileEntity(World w, int meta) {
			return new TileTCProcessor();
		}
	}
	
	public static class BlockBMProcessor extends BlockProcessor {
		
		public BlockBMProcessor() {
			this.name = "BM";
		}
		
		@Override
		public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
			TileEntity te = w.getTileEntity(x, y, z);
			if(te instanceof TileBMProcessor && ((TileBMProcessor)te).owner.equals("")){
				((TileBMProcessor)te).owner = player.getDisplayName();
				return true;
			}
			return super.onBlockActivated(w, x, y, z, player, side, hitX, hitY, hitZ);
		}
		
		@Override
		public TileEntity createNewTileEntity(World w, int meta) {
			return new TileBMProcessor();
		}
	}
	
	public static class BlockBOProcessor extends BlockProcessor implements IWandHUD {
		
		public BlockBOProcessor() {
			this.name = "BO";
		}
		
		@Override
		public TileEntity createNewTileEntity(World w, int meta) {
			return new TileBOProcessor();
		}

		@Override
		public void renderHUD(Minecraft minecraft, ScaledResolution res, World world, int x, int y, int z) {
			TileEntity tile = world.getTileEntity(x, y, z);
			if(tile instanceof TileBOProcessor) {
				((TileBOProcessor)tile).renderHUD(minecraft, res);
			}
		}
	}
}
