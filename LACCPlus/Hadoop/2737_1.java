//,temp,TestIPC.java,915,928,temp,TestIPC.java,783,797
//,3
public class xxx {
          @Override
          public void run() {
            Client client = new Client(LongWritable.class, clientConf);
            try {
              call(client, Thread.currentThread().getId(), addr, clientConf);
              callReturned.countDown();
              Thread.sleep(10000);
            } catch (IOException e) {
              LOG.error(e.toString());
            } catch (InterruptedException e) {
            } finally {
              client.stop();
            }
          }

};