//,temp,AccumulatingReducer.java,56,63,temp,NNBench.java,934,942
//,3
public class xxx {
    public NNBenchReducer () {
      LOG.info("Starting NNBenchReducer !!!");
      try {
        hostName = java.net.InetAddress.getLocalHost().getHostName();
      } catch(Exception e) {
        hostName = "localhost";
      }
      LOG.info("Starting NNBenchReducer on " + hostName);
    }

};