//,temp,NettyConsumerClientModeReconnectTest.java,139,143,temp,NettyConsumerClientModeReuseChannelTest.java,143,147
//,2
public class xxx {
        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
            LOG.warn("Unhandled exception caught: {}", cause.getMessage(), cause);
            ctx.close();
        }

};