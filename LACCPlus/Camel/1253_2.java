//,temp,DatagramPacketStringEncoder.java,59,75,temp,DatagramPacketObjectEncoder.java,43,58
//,3
public class xxx {
    @Override
    protected void encode(
            ChannelHandlerContext ctx, AddressedEnvelope<Object, InetSocketAddress> msg,
            List<Object> out)
            throws Exception {
        if (msg.content() instanceof Serializable) {
            Serializable payload = (Serializable) msg.content();
            ByteBuf buf = ctx.alloc().buffer();
            delegateObjectEncoder.encode(ctx, payload, buf);
            AddressedEnvelope<Object, InetSocketAddress> addressedEnvelop
                    = new DefaultAddressedEnvelope<>(buf, msg.recipient(), msg.sender());
            out.add(addressedEnvelop);
        } else {
            LOG.debug("Ignoring message content as it is not a java.io.Serializable instance.");
        }
    }

};