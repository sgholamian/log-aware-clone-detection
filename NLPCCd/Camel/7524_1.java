//,temp,sample_5303.java,2,16,temp,sample_5304.java,2,17
//,3
public class xxx {
protected QuartzEndpoint createEndpoint(final String uri, final String remaining, final Map<String, Object> parameters) throws Exception {
URI u = new URI(uri);
String path = ObjectHelper.after(u.getPath(), "/");
String host = u.getHost();
String cron = getAndRemoveParameter(parameters, "cron", String.class);
boolean fireNow = getAndRemoveParameter(parameters, "fireNow", Boolean.class, Boolean.FALSE);
Integer startDelayedSeconds = getAndRemoveParameter(parameters, "startDelayedSeconds", Integer.class);
if (startDelayedSeconds != null) {
if (scheduler.isStarted()) {


log.info("a quartz job is already started cannot apply the startdelayedseconds configuration");
}
}
}

};