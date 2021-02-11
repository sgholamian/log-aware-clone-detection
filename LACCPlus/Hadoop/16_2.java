//,temp,TestTaskImpl.java,420,432,temp,TestTaskImpl.java,394,405
//,3
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