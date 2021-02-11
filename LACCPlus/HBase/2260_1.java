//,temp,MultiThreadedReader.java,111,120,temp,MultiThreadedUpdater.java,81,92
//,2
public class xxx {
  @Override
  public void start(long startKey, long endKey, int numThreads) throws IOException {
    super.start(startKey, endKey, numThreads);
    if (verbose) {
      LOG.debug("Reading keys [" + startKey + ", " + endKey + ")");
    }

    addReaderThreads(numThreads);
    startThreads(readers);
  }

};