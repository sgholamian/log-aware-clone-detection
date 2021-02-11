//,temp,TestWALMonotonicallyIncreasingSeqId.java,165,181,temp,TestWALMonotonicallyIncreasingSeqId.java,140,155
//,3
public class xxx {
    @Override
    public void run() {
      try {
        for (int i = 0; i < 100; i++) {
          byte[] row = Bytes.toBytes("putRow" + i);
          Put put = new Put(row);
          put.addColumn(Bytes.toBytes("cf"), Bytes.toBytes(0), new byte[0]);
          latch.await();
          region.batchMutate(new Mutation[] { put });
          Thread.sleep(10);
        }

      } catch (Throwable t) {
        LOG.warn("Error happend when Increment: ", t);
      }
    }

};