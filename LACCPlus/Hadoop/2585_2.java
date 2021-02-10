//,temp,TestTaskImpl.java,447,458,temp,TestTaskImpl.java,420,430
//,3
public class xxx {
  @Test 
  /**
   * {@link TaskState#SCHEDULED}->{@link TaskState#KILL_WAIT}
   */
  public void testKillScheduledTask() {
    LOG.info("--- START: testKillScheduledTask ---");
    mockTask = createMockTask(TaskType.MAP);        
    TaskId taskId = getNewTaskID();
    scheduleTaskAttempt(taskId);
    killTask(taskId);
  }

};