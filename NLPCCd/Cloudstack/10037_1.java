//,temp,sample_8323.java,2,17,temp,sample_8322.java,2,16
//,3
public class xxx {
public void notifySubscribers(String subject, Object sender, EventArgs args) {
List<SubscriberInfo> l = getExecutableSubscriberList(subject);
if (l != null) {
for (SubscriberInfo info : l) {
try {
info.execute(sender, args);
} catch (IllegalArgumentException e) {
} catch (IllegalAccessException e) {
} catch (InvocationTargetException e) {


log.info("exception on notifying event subscribers");
}
}
}
}

};