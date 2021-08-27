//,temp,sample_3661.java,2,10,temp,sample_3660.java,2,9
//,3
public class xxx {
public int forceCompletionOfAllGroups() {
boolean allow = camelContext.getStatus().isStarted() || camelContext.getStatus().isStopping();
if (!allow) {


log.info("cannot start force completion of all groups because camelcontext has not been started");
}
}

};