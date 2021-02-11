//,temp,NettyServerCnxn.java,567,575,temp,NettyServerCnxn.java,557,565
//,3
public class xxx {
    @Override
    public void disableRecv(boolean waitDisableRecv) {
        if (throttled.compareAndSet(false, true)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Throttling - disabling recv {}", this);
            }
            channel.pipeline().fireUserEventTriggered(AutoReadEvent.DISABLE);
        }
    }

};