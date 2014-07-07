package democretes.blocks.machines;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import democretes.blocks.BlockBase;
import democretes.blocks.machines.tiles.TileEldritchConsumer;
import democretes.blocks.machines.tiles.TileEldritchConsumer.Range;
import democretes.lib.Names;
import democretes.lib.Ref;
import democretes.lib.RenderIds;

public class BlockEldritchConsumer extends BlockBase{

	public BlockEldritchConsumer(int id) {
		super(id);
		setUnlocalizedName(Ref.getId(Names.eldritchConsumer));
		
	}
	
	@Override
	public boolean onBlockActivated(World w, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ) {
		TileEntity te = w.getBlockTileEntity(x, y, z);
		if(te==null || !(te instanceof TileEldritchConsumer)){
			return false;
		}
		if(!w.isRemote){
			if(player.isSneaking()){
				Range c = ((TileEldritchConsumer)te).current = ((TileEldritchConsumer)te).current.getNext();
				player.sendChatToPlayer(ChatMessageComponent.createFromText("Set to: "+c.toString()));
				player.sendChatToPlayer(ChatMessageComponent.createFromText("Range: "+Integer.toString(c.r*2 + 1)+"x"+Integer.toString(c.r*2 + 1)));
				player.sendChatToPlayer(ChatMessageComponent.createFromText("Depth: "+(c.h==-1 ? "To BedRock" : c.h)));
				return true;
			} else {
				Range c = ((TileEldritchConsumer)te).current;
				player.sendChatToPlayer(ChatMessageComponent.createFromText("Processing as: " + c.toString()));
				player.sendChatToPlayer(ChatMessageComponent.createFromText("Range: "+Integer.toString(c.r*2 + 1)+"x"+Integer.toString(c.r*2 + 1)));
				player.sendChatToPlayer(ChatMessageComponent.createFromText("Depth: " + (c.h==-1 ? "To BedRock" : c.h)));
				return true;
			}
		}
		return true;
	}
	
	@Override
	public boolean isOpaqueCube() {
		return false;
	}
	
	@Override
	public int getRenderType() {
		return RenderIds.idEldrichConsumer;
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEldritchConsumer();
	}
	
	@Override
	public void registerIcons(IconRegister reg) {
		blockIcon = reg.registerIcon(Ref.getAsset("eldCons"));
	}
	
	@Override
	public Icon getIcon(int par1, int par2) {
		return blockIcon;
	}
	
	@Override
	public boolean isBlockNormalCube(World world, int x, int y, int z) {
		return false;
	}
}
