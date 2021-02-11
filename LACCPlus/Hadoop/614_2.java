//,temp,TestTaskImpl.java,420,432,temp,TestTaskImpl.java,407,418
//,3
public class xxx {
  @Test 
  /**
   * Launch attempt
   * {@link TaskState#SCHEDULED}->{@link TaskState#RUNNING}
   */
  public void testLaunchTaskAttempt() {
    LOG.info("--- START: testLaunchTaskAttempt ---");
    mockTask = createMockTask(TaskType.MAP);        
    TaskId taskId = getNewTaskID();
    scheduleTaskAttempt(taskId);
    launchTaskAttempt(getLastAttempt().getAttemptId());
  }

};