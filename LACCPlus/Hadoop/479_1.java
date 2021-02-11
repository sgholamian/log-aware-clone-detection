//,temp,TestRecovery.java,1597,1652,temp,TestRecovery.java,1538,1595
//,3
public class xxx {
  @Test
  public void testRecoveryTaskSuccessAllAttemptsSucceed() {
    LOG.info("--- START:  testRecoveryTaskSuccessAllAttemptsFail ---");

    long clusterTimestamp = System.currentTimeMillis();
    EventHandler mockEventHandler = mock(EventHandler.class);
    MapTaskImpl recoverMapTask = getMockMapTask(clusterTimestamp,
        mockEventHandler);

    TaskId taskId = recoverMapTask.getID();
    JobID jobID = new JobID(Long.toString(clusterTimestamp), 1);
    TaskID taskID = new TaskID(jobID,
        org.apache.hadoop.mapreduce.TaskType.MAP, taskId.getId());

    //Mock up the TaskAttempts
    Map<TaskAttemptID, TaskAttemptInfo> mockTaskAttempts =
        new HashMap<TaskAttemptID, TaskAttemptInfo>();

    TaskAttemptID taId1 = new TaskAttemptID(taskID, 2);
    TaskAttemptInfo mockTAinfo1 = getMockTaskAttemptInfo(taId1,
        TaskAttemptState.SUCCEEDED);
    mockTaskAttempts.put(taId1, mockTAinfo1);

    TaskAttemptID taId2 = new TaskAttemptID(taskID, 1);
    TaskAttemptInfo mockTAinfo2 = getMockTaskAttemptInfo(taId2,
        TaskAttemptState.SUCCEEDED);
    mockTaskAttempts.put(taId2, mockTAinfo2);

    OutputCommitter mockCommitter = mock (OutputCommitter.class);
    TaskInfo mockTaskInfo = mock(TaskInfo.class);
    when(mockTaskInfo.getTaskStatus()).thenReturn("SUCCEEDED");
    when(mockTaskInfo.getTaskId()).thenReturn(taskID);
    when(mockTaskInfo.getAllTaskAttempts()).thenReturn(mockTaskAttempts);

    recoverMapTask.handle(
        new TaskRecoverEvent(taskId, mockTaskInfo, mockCommitter, true));

    ArgumentCaptor<Event> arg = ArgumentCaptor.forClass(Event.class);
    verify(mockEventHandler,atLeast(1)).handle(
        (org.apache.hadoop.yarn.event.Event) arg.capture());

    Map<TaskAttemptID, TaskAttemptState> finalAttemptStates =
        new HashMap<TaskAttemptID, TaskAttemptState>();
    finalAttemptStates.put(taId1, TaskAttemptState.SUCCEEDED);
    finalAttemptStates.put(taId2, TaskAttemptState.SUCCEEDED);

    List<EventType> jobHistoryEvents = new ArrayList<EventType>();
    jobHistoryEvents.add(EventType.TASK_STARTED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_STARTED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_FINISHED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_STARTED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_FINISHED);
    jobHistoryEvents.add(EventType.TASK_FINISHED);
    recoveryChecker(recoverMapTask, TaskState.SUCCEEDED, finalAttemptStates,
        arg, jobHistoryEvents, 2L, 0L);
  }

};