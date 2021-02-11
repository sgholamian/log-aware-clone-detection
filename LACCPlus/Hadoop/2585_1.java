//,temp,TestTaskImpl.java,447,458,temp,TestTaskImpl.java,420,430
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