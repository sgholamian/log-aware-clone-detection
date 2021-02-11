//,temp,TestTaskImpl.java,420,432,temp,TestTaskImpl.java,394,405
//,3
public class xxx {
  @Test
  /**
   * Kill running attempt
   * {@link TaskState#RUNNING}->{@link TaskState#RUNNING} 
   */
  public void testKillRunningTaskAttempt() {
    LOG.info("--- START: testKillRunningTaskAttempt ---");
    mockTask = createMockTask(TaskType.MAP);        
    TaskId taskId = getNewTaskID();
    scheduleTaskAttempt(taskId);
    launchTaskAttempt(getLastAttempt().getAttemptId());
    killRunningTaskAttempt(getLastAttempt().getAttemptId());    
  }

};