//,temp,NettySSLConsumerClientModeTest.java,155,159,temp,NettyConsumerClientModeTest.java,127,131
//,2
public class xxx {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            LOG.warn("Unhandled exception caught: {}", cause.getMessage(), cause);
            ctx.close();
        }

};