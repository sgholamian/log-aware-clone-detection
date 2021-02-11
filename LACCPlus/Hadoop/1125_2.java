//,temp,TestTaskImpl.java,407,418,temp,TestTaskImpl.java,394,405
//,2
public class xxx {
  @Test 
  /**
   * Kill attempt
   * {@link TaskState#SCHEDULED}->{@link TaskState#SCHEDULED}
   */
  public void testKillScheduledTaskAttempt() {
    LOG.info("--- START: testKillScheduledTaskAttempt ---");
    mockTask = createMockTask(TaskType.MAP);        
    TaskId taskId = getNewTaskID();
    scheduleTaskAttempt(taskId);
    killScheduledTaskAttempt(getLastAttempt().getAttemptId());
  }

};