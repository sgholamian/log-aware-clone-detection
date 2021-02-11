//,temp,TestTokenAuthentication.java,247,253,temp,MockServer.java,72,77
//,3
public class xxx {
    @Override
    public void abort(String reason, Throwable error) {
      LOG.error(HBaseMarkers.FATAL, "Aborting on: "+reason, error);
      this.aborted = true;
      this.stopped = true;
      sleeper.skipSleepCycle();
    }

};