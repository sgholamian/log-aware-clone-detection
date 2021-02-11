//,temp,FsVolumeList.java,394,437,temp,FsVolumeList.java,178,223
//,3
public class xxx {
  void addBlockPool(final String bpid, final Configuration conf) throws IOException {
    long totalStartTime = Time.monotonicNow();
    
    final List<IOException> exceptions = Collections.synchronizedList(
        new ArrayList<IOException>());
    List<Thread> blockPoolAddingThreads = new ArrayList<Thread>();
    for (final FsVolumeImpl v : volumes.get()) {
      Thread t = new Thread() {
        public void run() {
          try (FsVolumeReference ref = v.obtainReference()) {
            FsDatasetImpl.LOG.info("Scanning block pool " + bpid +
                " on volume " + v + "...");
            long startTime = Time.monotonicNow();
            v.addBlockPool(bpid, conf);
            long timeTaken = Time.monotonicNow() - startTime;
            FsDatasetImpl.LOG.info("Time taken to scan block pool " + bpid +
                " on " + v + ": " + timeTaken + "ms");
          } catch (ClosedChannelException e) {
            // ignore.
          } catch (IOException ioe) {
            FsDatasetImpl.LOG.info("Caught exception while scanning " + v +
                ". Will throw later.", ioe);
            exceptions.add(ioe);
          }
        }
      };
      blockPoolAddingThreads.add(t);
      t.start();
    }
    for (Thread t : blockPoolAddingThreads) {
      try {
        t.join();
      } catch (InterruptedException ie) {
        throw new IOException(ie);
      }
    }
    if (!exceptions.isEmpty()) {
      throw exceptions.get(0);
    }
    
    long totalTimeTaken = Time.monotonicNow() - totalStartTime;
    FsDatasetImpl.LOG.info("Total time to scan all replicas for block pool " +
        bpid + ": " + totalTimeTaken + "ms");
  }

};