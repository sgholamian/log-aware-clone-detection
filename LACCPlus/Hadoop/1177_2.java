//,temp,FsVolumeList.java,402,418,temp,FsVolumeList.java,188,205
//,3
public class xxx {
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