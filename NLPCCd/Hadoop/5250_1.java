//,temp,sample_6845.java,2,17,temp,sample_6844.java,2,17
//,3
public class xxx {
public void dummy_method(){
Assert.assertEquals(2, mapTask1.getAttempts().size());
Iterator<TaskAttempt> itr = mapTask1.getAttempts().values().iterator();
itr.next();
TaskAttempt task1Attempt2 = itr.next();
waitForContainerAssignment(task1Attempt2);
app.getContext().getEventHandler().handle( new TaskAttemptEvent(task1Attempt2.getID(), TaskAttemptEventType.TA_CONTAINER_LAUNCH_FAILED));
app.waitForState(task1Attempt2, TaskAttemptState.FAILED);
timeOut = 0;
while (mapTask1.getAttempts().size() != 3 && timeOut++ < 10) {
Thread.sleep(2000);


log.info("waiting for next attempt to start");
}
}

};