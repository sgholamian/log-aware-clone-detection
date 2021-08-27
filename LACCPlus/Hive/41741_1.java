//,temp,LlapRecordReader.java,585,592,temp,LlapRecordReader.java,576,583
//,3
public class xxx {
  @Override
  public void consumeData(ColumnVectorBatch data) throws InterruptedException {
    if (LlapIoImpl.LOG.isTraceEnabled()) {
      LlapIoImpl.LOG.trace("consume called; closed {}, interrupted {}, err {}, pending {}",
          isClosed, isInterrupted, pendingError.get(), queue.size());
    }
    enqueueInternal(data);
  }

};