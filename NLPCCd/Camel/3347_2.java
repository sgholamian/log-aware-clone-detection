//,temp,sample_6328.java,2,15,temp,sample_6327.java,2,12
//,3
public class xxx {
private static boolean doNotifyEvent(EventNotifier notifier, EventObject event) {
boolean started = true;
if (notifier instanceof StatefulService) {
started = ((StatefulService) notifier).isStarted();
}
if (!started) {


log.info("ignoring notifying event the eventnotifier has not been started yet");
}
}

};