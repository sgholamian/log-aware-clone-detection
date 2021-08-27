//,temp,sample_3483.java,2,11,temp,sample_3484.java,2,16
//,3
public class xxx {
public void dummy_method(){
while(retryCount < this.maxTaskCancelRetryCount && !future.isDone()) {
if (future.cancel(true)) {
return;
}
retryCount++;
try {
Thread.sleep(this.maxTaskCancelRetryWaitTimeInMs);
} catch (InterruptedException e) {
}
}


log.info("failed to cancel the job iscancelled retry count");
}

};