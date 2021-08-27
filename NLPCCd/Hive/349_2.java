//,temp,sample_2959.java,2,16,temp,sample_2965.java,2,19
//,3
public class xxx {
public void dummy_method(){
while (!isShutdown.get() && !Thread.currentThread().isInterrupted()) {
scheduleLock.lock();
try {
while (!pendingScheduleInvocations.get()) {
scheduleCondition.await();
}
} catch (InterruptedException e) {
if (isShutdown.get()) {
break;
} else {


log.info("scheduler thread interrupted without being shutdown");
}
}
}
}

};