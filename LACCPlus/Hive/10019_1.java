//,temp,ChannelOutputStream.java,61,68,temp,WritableByteChannelAdapter.java,70,77
//,2
public class xxx {
    @Override
    public void operationComplete(ChannelFuture future) {
      if (future.isCancelled()) {
        LOG.error("Close cancelled on ID " + id);
      } else if (!future.isSuccess()) {
        LOG.error("Close failed on ID " + id, future.cause());
      }
    }

};