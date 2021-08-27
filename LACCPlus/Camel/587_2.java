//,temp,ClientChannelHandler.java,50,64,temp,ServerChannelHandler.java,61,70
//,3
public class xxx {
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        if (LOG.isTraceEnabled()) {
            LOG.trace("Channel closed: {}", ctx.channel());
        }
        // to keep track of open sockets
        consumer.getNettyServerBootstrapFactory().removeChannel(ctx.channel());

        super.channelInactive(ctx);
    }

};