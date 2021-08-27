//,temp,LlapRecordReader.java,585,592,temp,LlapRecordReader.java,576,583
//,3
public class xxx {
  @Override
  public void setDone() throws InterruptedException {
    if (LlapIoImpl.LOG.isDebugEnabled()) {
      LlapIoImpl.LOG.debug("setDone called; closed {}, interrupted {}, err {}, pending {}",
          isClosed, isInterrupted, pendingError.get(), queue.size());
    }
    enqueueInternal(DONE_OBJECT);
  }

};