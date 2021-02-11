//,temp,FsVolumeList.java,394,437,temp,FsVolumeList.java,178,223
//,3
public class xxx {
  void getAllVolumesMap(final String bpid,
                        final ReplicaMap volumeMap,
                        final RamDiskReplicaTracker ramDiskReplicaMap)
      throws IOException {
    long totalStartTime = Time.monotonicNow();
    final List<IOException> exceptions = Collections.synchronizedList(
        new ArrayList<IOException>());
    List<Thread> replicaAddingThreads = new ArrayList<Thread>();
    for (final FsVolumeImpl v : volumes.get()) {
      Thread t = new Thread() {
        public void run() {
          try (FsVolumeReference ref = v.obtainReference()) {
            FsDatasetImpl.LOG.info("Adding replicas to map for block pool " +
                bpid + " on volume " + v + "...");
            long startTime = Time.monotonicNow();
            v.getVolumeMap(bpid, volumeMap, ramDiskReplicaMap);
            long timeTaken = Time.monotonicNow() - startTime;
            FsDatasetImpl.LOG.info("Time to add replicas to map for block pool"
                + " " + bpid + " on volume " + v + ": " + timeTaken + "ms");
          } catch (ClosedChannelException e) {
            FsDatasetImpl.LOG.info("The volume " + v + " is closed while " +
                "addng replicas, ignored.");
          } catch (IOException ioe) {
            FsDatasetImpl.LOG.info("Caught exception while adding replicas " +
                "from " + v + ". Will throw later.", ioe);
            exceptions.add(ioe);
          }
        }
      };
      replicaAddingThreads.add(t);
      t.start();
    }
    for (Thread t : replicaAddingThreads) {
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
    FsDatasetImpl.LOG.info("Total time to add all replicas to map: "
        + totalTimeTaken + "ms");
  }

};