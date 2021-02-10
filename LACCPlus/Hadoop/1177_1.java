//,temp,FsVolumeList.java,402,418,temp,FsVolumeList.java,188,205
//,3
public class xxx {
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