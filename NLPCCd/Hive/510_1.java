//,temp,sample_3483.java,2,11,temp,sample_3484.java,2,16
//,3
public class xxx {
private void cancelExecutePoolThread(Future<T> future) {
int retryCount = 0;
while(retryCount < this.maxTaskCancelRetryCount && !future.isDone()) {
if (future.cancel(true)) {


log.info("cancel job request issued successfully");
}
}
}

};