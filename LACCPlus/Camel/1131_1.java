//,temp,DatagramPacketEncoder.java,36,47,temp,DatagramPacketDelimiterDecoder.java,44,58
//,3
public class xxx {
    @Override
    protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<Object, InetSocketAddress> msg, List<Object> out)
            throws Exception {
        if (msg.content() instanceof ByteBuf) {
            ByteBuf payload = (ByteBuf) msg.content();
            // Just wrap the message as DatagramPacket, need to make sure the message content is ByteBuf
            DatagramPacket dp = new DatagramPacket(payload.retain(), msg.recipient());
            out.add(dp);
        } else {
            LOG.debug("Ignoring message content as it is not an io.netty.buffer.ByteBuf instance.");
        }
    }

};