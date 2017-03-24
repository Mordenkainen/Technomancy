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
import theflogat.technomancy.common.player.PlayerData;
import theflogat.technomancy.common.player.PlayerData.Affinity;
import theflogat.technomancy.lib.Reference;
import theflogat.technomancy.util.Loc;
import cpw.mods.fml.common.network.FMLEmbeddedChannel;
import cpw.mods.fml.common.network.FMLIndexedMessageToMessageCodec;
import cpw.mods.fml.common.network.FMLOutboundHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;

public class PacketHandler {

    public EnumMap<Side, FMLEmbeddedChannel> channels;
    public static PacketHandler instance;

    public PacketHandler() {
        channels = NetworkRegistry.INSTANCE.newChannel(Reference.MOD_ID, new TechnomChannelHandler());
        if (Loc.isClient()) {
            final FMLEmbeddedChannel channel = channels.get(Side.CLIENT);

            final String handler = channel.findChannelHandlerNameForType(TechnomChannelHandler.class);
            channel.pipeline().addAfter(handler, "ExistenceStatsHandler", new ExistenceStatsHandler());
        }
    }

    public static class BaseMessage {

        public int id;
    }

    public static class ExistenceStats extends BaseMessage {

        public int exLevel;
        public int exPower;
        public int[] affinities;
    }

    public static class ExistenceStatsHandler extends SimpleChannelInboundHandler<ExistenceStats> {

        @Override
        protected void channelRead0(final ChannelHandlerContext ctx, final ExistenceStats stats) throws Exception { // NOPMD
            final NBTTagCompound data = PlayerData.getData(Minecraft.getMinecraft().thePlayer);
            data.setInteger("existencelevel", stats.exLevel);
            data.setInteger("existencepower", stats.exPower);
            for (int i = 0; i < Affinity.allAff.length; i++) {
                data.setInteger(Affinity.allAff[i].getName(), stats.affinities[i]);
            }
        }
    }

    public static Packet getPacket(final EntityPlayerMP player) {
        final NBTTagCompound data = PlayerData.getData(player);
        final ExistenceStats stats = new ExistenceStats();
        stats.id = 0;
        stats.exLevel = data.getInteger("existencelevel");
        stats.exPower = data.getInteger("existencepower");
        stats.affinities = new int[Affinity.allAff.length];
        for (int i = 0; i < Affinity.allAff.length; i++) {
            stats.affinities[i] = data.getInteger(Affinity.allAff[i].getName());
        }
        return instance.channels.get(Side.SERVER).generatePacketFrom(stats);
    }

    public class TechnomChannelHandler extends FMLIndexedMessageToMessageCodec<BaseMessage> {

        public TechnomChannelHandler() {
            super();
            addDiscriminator(0, ExistenceStats.class);
        }

        @Override
        public void encodeInto(final ChannelHandlerContext ctx, final BaseMessage msg, final ByteBuf target) throws Exception { // NOPMD
            target.writeInt(msg.id);
            if (msg.id == 0) {
                target.writeInt(((ExistenceStats) msg).exLevel);
                target.writeInt(((ExistenceStats) msg).exPower);
                for (int i = 0; i < Affinity.allAff.length; i++) {
                    target.writeInt(((ExistenceStats) msg).affinities[i]);
                }
            }
        }

        @Override
        public void decodeInto(final ChannelHandlerContext ctx, final ByteBuf source, final BaseMessage msg) {
            if (source.readInt() == 0) {
                ((ExistenceStats) msg).exLevel = source.readInt();
                ((ExistenceStats) msg).exPower = source.readInt();
                ((ExistenceStats) msg).affinities = new int[Affinity.allAff.length];
                for (int i = 0; i < Affinity.allAff.length; i++) {
                    ((ExistenceStats) msg).affinities[i] = source.readInt();
                }
            }
        }
    }

    public void sendTo(final Packet message, final EntityPlayerMP player) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.PLAYER);
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGETARGS).set(player);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToAll(final Packet message) {
        channels.get(Side.SERVER).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.ALL);
        channels.get(Side.SERVER).writeAndFlush(message);
    }

    public void sendToServer(final Packet message) {
        channels.get(Side.CLIENT).attr(FMLOutboundHandler.FML_MESSAGETARGET).set(FMLOutboundHandler.OutboundTarget.TOSERVER);
        channels.get(Side.CLIENT).writeAndFlush(message).addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
    }
}