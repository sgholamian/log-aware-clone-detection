//,temp,sample_4942.java,2,21,temp,sample_3996.java,2,19
//,3
public class xxx {
public void dummy_method(){
if (initialApps.isEmpty()) {
} else {
Iterator<ApplicationId> it = initialApps.iterator();
while (it.hasNext()) {
ApplicationId id = it.next();
try {
if (!taskAppChecker.isApplicationActive(id)) {
it.remove();
}
} catch (YarnException e) {


log.info("exception while checking the app status will leave the entry in the list");
}
}
}
}

};