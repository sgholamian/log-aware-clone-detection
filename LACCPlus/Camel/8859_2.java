//,temp,SingleTCPNettyServerBootstrapFactory.java,190,206,temp,SingleUDPNettyServerBootstrapFactory.java,199,211
//,3
public class xxx {
    protected void stopServerBootstrap() {
        // close all channels
        LOG.info("ConnectionlessBootstrap disconnecting from {}:{}", configuration.getHost(), configuration.getPort());

        LOG.trace("Closing {} channels", allChannels.size());
        allChannels.close().awaitUninterruptibly();

        // and then shutdown the thread pools
        if (workerGroup != null) {
            workerGroup.shutdownGracefully();
            workerGroup = null;
        }
    }

};