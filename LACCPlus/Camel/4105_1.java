//,temp,DatagramPacketByteArrayEncoder.java,38,50,temp,DatagramPacketByteArrayDecoder.java,38,50
//,3
public class xxx {
    @Override
    protected void encode(ChannelHandlerContext ctx, AddressedEnvelope<Object, InetSocketAddress> msg, List<Object> out)
            throws Exception {
        if (msg.content() instanceof byte[]) {
            delegateEncoder.encode(ctx, (byte[]) msg.content(), out);
            ByteBuf buf = (ByteBuf) out.remove(out.size() - 1);
            AddressedEnvelope<Object, InetSocketAddress> addressedEnvelop
                    = new DefaultAddressedEnvelope<>(buf.retain(), msg.recipient(), msg.sender());
            out.add(addressedEnvelop);
        } else {
            LOG.debug("Ignoring message content as it is not a byte[] instance.");
        }
    }

};