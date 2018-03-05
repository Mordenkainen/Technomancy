package theflogat.technomancy.network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import java.util.EnumMap;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.network.FMLEmbeddedChannel;
import net.minecraftforge.fml.common.network.FMLIndexedMessageToMessageCodec;
import net.minecraftforge.fml.common.network.FMLOutboundHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.player.PlayerData.Affinity;
import theflogat.technomancy.lib.Ref;
import theflogat.technomancy.util.Loc;

public class PacketHandler {

	public EnumMap<Side, FMLEmbeddedChannel> channels;
	public static PacketHandler instance;

	public PacketHandler() {
		instance = this;
		channels = NetworkRegistry.INSTANCE.newChannel(Ref.MOD_ID, new TechnomChannelHandler());
		if(Loc.isClient()){
			FMLEmbeddedChannel channel = channels.get(Side.CLIENT);

			String handler = channel.findChannelHandlerNameForType(TechnomChannelHandler.class);
			//channel.pipeline().addAfter(handler, "ExistenceStatsHandler", new ExistenceStatsHandler());
		}
	}

	public static class BaseMessage {
		int id;
	}

	public static class ExistenceStats extends BaseMessage{
		int exLevel;
		int exPower;
		int[] affinities;
	}

	public static class ExistenceStatsHandler extends SimpleChannelInboundHandler<ExistenceStats>{
		@Override
		protected void channelRead0(ChannelHandlerContext ctx, ExistenceStats stats) {
			NBTTagCompound data;
			if (PlayerData.getData(Minecraft.getMinecraft().player) != null) {
				data = PlayerData.getData(Minecraft.getMinecraft().player);
			}else {
				data = new NBTTagCompound();
			}
			data.setInteger("existencelevel", stats.exLevel);
			data.setInteger("existencepower", stats.exPower);
			for(int i=0;i<Affinity.allAff.length;i++){
				data.setInteger(Affinity.allAff[i].getName(), stats.affinities[i]);
			}
		}
	}

	public static Packet getPacket(EntityPlayerMP player){
		NBTTagCompound data = PlayerData.getData(player);
		ExistenceStats stats = new ExistenceStats();
		stats.id = 0;
		stats.exLevel = data.getInteger("existencelevel");
		stats.exPower = data.getInteger("existencepower");
		stats.affinities = new int[Affinity.allAff.length];
		for(int i=0;i<Affinity.allAff.length;i++){
			stats.affinities[i] = data.getInteger(Affinity.allAff[i].getName());
		}
		return instance.channels.get(Side.SERVER).generatePacketFrom(stats);
	}

	public class TechnomChannelHandler extends FMLIndexedMessageToMessageCodec<BaseMessage> {

		public TechnomChannelHandler() {
			addDiscriminator(0, ExistenceStats.class);
		}

		@Override
		public void encodeInto(ChannelHandlerContext ctx, BaseMessage msg, ByteBuf target) throws Exception {
			target.writeInt(msg.id);
			switch(msg.id){
			case 0:
				target.writeInt(((ExistenceStats)msg).exLevel);
				target.writeInt(((ExistenceStats)msg).exPower);
				for(int i=0;i<Affinity.allAff.length;i++){
					target.writeInt(((ExistenceStats)msg).affinities[i]);
				}
			}
		}


		@Override
		public void decodeInto(ChannelHandlerContext ctx, ByteBuf source, BaseMessage msg) {
			switch(source.readInt()){
			case 0:
				((ExistenceStats)msg).exLevel = source.readInt();
				((ExistenceStats)msg).exPower = source.readInt();
				((ExistenceStats)msg).affinities = new int[Affinity.allAff.length];
				for(int i=0;i<Affinity.allAff.length;i++){
					((ExistenceStats)msg).affinities[i] = source.readInt();
				}
			}
		}
	}

	public void sendTo(Packet message, EntityPlayerMP player){
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
		channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToAll(Packet message){
		channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
		channels.get(Side.SERVER).writeAndFlush(message);
	}

	public void sendToServer(Packet message){
		channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
		channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}
}