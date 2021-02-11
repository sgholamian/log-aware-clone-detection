//,temp,TestIPC.java,915,928,temp,TestIPC.java,783,797
//,3
public class xxx {
        @Override
        public void run() {
          Client client = new Client(LongWritable.class, conf);
          try {
            call(client, new LongWritable(Thread.currentThread().getId()),
                addr, 60000, conf);
          } catch (Throwable e) {
            LOG.error(e.toString());
            failures.incrementAndGet();
            return;
          } finally {
            callFinishedLatch.countDown();            
            client.stop();
          }
        }

};