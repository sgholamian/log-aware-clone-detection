//,temp,TestWALMonotonicallyIncreasingSeqId.java,165,181,temp,TestWALMonotonicallyIncreasingSeqId.java,140,155
//,3
public class xxx {
    @Override
    public void run() {
      try {
        for (int i = 0; i < 100; i++) {
          byte[] row = Bytes.toBytes("incrementRow" + i);
          Increment inc = new Increment(row);
          inc.addColumn(Bytes.toBytes("cf"), Bytes.toBytes(0), 1);
          // inc.setDurability(Durability.ASYNC_WAL);
          region.increment(inc);
          latch.countDown();
          Thread.sleep(10);
        }

      } catch (Throwable t) {
        LOG.warn("Error happend when Put: ", t);
      }
    }

};