//,temp,ClientChannelHandler.java,50,64,temp,ServerChannelHandler.java,61,70
//,3
public class xxx {
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Channel open: {}", ctx.channel());
        }
        // to keep track of open sockets
        producer.getAllChannels().add(ctx.channel());

        // reset flags
        disconnecting = false;
        messageReceived = false;
        exceptionHandled = false;

        super.channelActive(ctx);
    }

};