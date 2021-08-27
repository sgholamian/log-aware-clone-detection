//,temp,NettyTcpTransport.java,225,236,temp,NettyWSTransport.java,81,92
//,3
public class xxx {
    @Override
    public void send(ByteBuf output) throws IOException {
        checkConnected();
        int length = output.readableBytes();
        if (length == 0) {
            return;
        }

        LOG.trace("Attempted write of: {} bytes", length);

        channel.writeAndFlush(output);
    }

};