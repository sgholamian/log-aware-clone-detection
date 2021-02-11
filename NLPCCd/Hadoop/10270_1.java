//,temp,sample_7654.java,2,15,temp,sample_7653.java,2,13
//,3
public class xxx {
public void resolve(TaskCompletionEvent event) {
switch (event.getTaskStatus()) {
case SUCCEEDED: URI u = getBaseURI(reduceId, event.getTaskTrackerHttp());
addKnownMapOutput(u.getHost() + ":" + u.getPort(), u.toString(), event.getTaskAttemptId());
maxMapRuntime = Math.max(maxMapRuntime, event.getTaskRunTime());
break;
case FAILED: case KILLED: case OBSOLETE: obsoleteMapOutput(event.getTaskAttemptId());
break;
case TIPFAILED: tipFailed(event.getTaskAttemptId().getTaskID());


log.info("ignoring output of failed map tip");
}
}

};