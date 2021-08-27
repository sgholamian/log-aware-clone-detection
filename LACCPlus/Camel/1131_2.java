//,temp,DatagramPacketEncoder.java,36,47,temp,DatagramPacketDelimiterDecoder.java,44,58
//,3
public class xxx {
    @Override
    protected void decode(
            ChannelHandlerContext ctx, AddressedEnvelope<Object, InetSocketAddress> msg,
            List<Object> out)
            throws Exception {
        if (msg.content() instanceof ByteBuf) {
            ByteBuf payload = (ByteBuf) msg.content();
            Object result = delegateDecoder.decode(ctx, payload);
            AddressedEnvelope<Object, InetSocketAddress> addressEvelop
                    = new DefaultAddressedEnvelope<>(result, msg.recipient(), msg.sender());
            out.add(addressEvelop);
        } else {
            LOG.debug("Ignoring message content as it is not an io.netty.buffer.ByteBuf instance.");
        }
    }

};