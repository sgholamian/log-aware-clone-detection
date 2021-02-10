//,temp,BlockReceiver.java,391,398,temp,Dispatcher.java,694,697
//,3
public class xxx {
  synchronized boolean packetSentInTime() {
    long diff = Time.monotonicNow() - lastSentTime;
    if (diff > maxSendIdleTime) {
      LOG.info("A packet was last sent " + diff + " milliseconds ago.");
      return false;
    }
    return true;
  }

};