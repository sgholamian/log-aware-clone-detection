//,temp,DatagramPacketByteArrayEncoder.java,38,50,temp,DatagramPacketByteArrayDecoder.java,38,50
//,3
public class xxx {
    @Override
    protected void decode(ChannelHandlerContext ctx, AddressedEnvelope<Object, InetSocketAddress> msg, List<Object> out)
            throws Exception {
        if (msg.content() instanceof ByteBuf) {
            delegateDecoder.decode(ctx, (ByteBuf) msg.content(), out);
            byte[] content = (byte[]) out.remove(out.size() - 1);
            AddressedEnvelope<Object, InetSocketAddress> addressedEnvelop
                    = new DefaultAddressedEnvelope<>(content, msg.recipient(), msg.sender());
            out.add(addressedEnvelop);
        } else {
            LOG.debug("Ignoring message content as it is not an io.netty.buffer.ByteBuf instance.");
        }
    }

};