//,temp,NettyServerCnxn.java,567,575,temp,NettyServerCnxn.java,557,565
//,3
public class xxx {
    @Override
    public void enableRecv() {
        if (throttled.compareAndSet(true, false)) {
            if (LOG.isDebugEnabled()) {
                LOG.debug("Sending unthrottle event {}", this);
            }
            channel.pipeline().fireUserEventTriggered(AutoReadEvent.ENABLE);
        }
    }

};