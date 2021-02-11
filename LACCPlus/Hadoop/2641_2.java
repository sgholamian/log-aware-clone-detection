//,temp,BlockReceiver.java,391,398,temp,Dispatcher.java,694,697
//,3
public class xxx {
    synchronized private void activateDelay(long delta) {
      delayUntil = Time.monotonicNow() + delta;
      LOG.info(this + " activateDelay " + delta/1000.0 + " seconds");
    }

};