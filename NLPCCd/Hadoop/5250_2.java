//,temp,sample_6845.java,2,17,temp,sample_6844.java,2,17
//,3
public class xxx {
public void dummy_method(){
TaskAttempt task1Attempt1 = mapTask1.getAttempts().values().iterator().next();
TaskAttempt task2Attempt = mapTask2.getAttempts().values().iterator().next();
app.waitForState(task1Attempt1, TaskAttemptState.RUNNING);
app.waitForState(task2Attempt, TaskAttemptState.RUNNING);
app.waitForState(reduceTask, TaskState.RUNNING);
app.getContext().getEventHandler().handle( new TaskAttemptEvent( task1Attempt1.getID(), TaskAttemptEventType.TA_FAILMSG));
app.waitForState(task1Attempt1, TaskAttemptState.FAILED);
int timeOut = 0;
while (mapTask1.getAttempts().size() != 2 && timeOut++ < 10) {
Thread.sleep(2000);


log.info("waiting for next attempt to start");
}
}

};