//,temp,sample_8321.java,2,15,temp,sample_8322.java,2,16
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


log.info("exception on notifying event subscribers");
}
}
}
}

};