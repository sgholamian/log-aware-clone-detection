//,temp,DatagramPacketStringEncoder.java,59,75,temp,DatagramPacketObjectEncoder.java,43,58
//,3
public class xxx {
    @Override
    protected void encode(
            ChannelHandlerContext ctx, AddressedEnvelope<Object, InetSocketAddress> msg,
            List<Object> out)
            throws Exception {
        if (msg.content() instanceof CharSequence) {
            CharSequence payload = (CharSequence) msg.content();
            if (payload.length() == 0) {
                return;
            }
            AddressedEnvelope<Object, InetSocketAddress> addressedEnvelop = new DefaultAddressedEnvelope<>(
                    ByteBufUtil.encodeString(ctx.alloc(), CharBuffer.wrap(payload), charset), msg.recipient(), msg.sender());
            out.add(addressedEnvelop);
        } else {
            LOG.debug("Ignoring message content as it is not a java.lang.CharSequence instance.");
        }
    }

};