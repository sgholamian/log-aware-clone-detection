//,temp,TestRecovery.java,1820,1874,temp,TestRecovery.java,1704,1761
//,3
public class xxx {
  @Test
  public void testRecoveryTaskSuccessAllAttemptsFail() {
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
        TaskAttemptState.FAILED);
    mockTaskAttempts.put(taId1, mockTAinfo1);

    TaskAttemptID taId2 = new TaskAttemptID(taskID, 1);
    TaskAttemptInfo mockTAinfo2 = getMockTaskAttemptInfo(taId2,
        TaskAttemptState.FAILED);
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
    finalAttemptStates.put(taId1, TaskAttemptState.FAILED);
    finalAttemptStates.put(taId2, TaskAttemptState.FAILED);
    // check for one new attempt launched since successful attempt not found
    TaskAttemptID taId3 = new TaskAttemptID(taskID, 2000);
    finalAttemptStates.put(taId3, TaskAttemptState.NEW);

    List<EventType> jobHistoryEvents = new ArrayList<EventType>();
    jobHistoryEvents.add(EventType.TASK_STARTED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_STARTED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_FAILED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_STARTED);
    jobHistoryEvents.add(EventType.MAP_ATTEMPT_FAILED);
    recoveryChecker(recoverMapTask, TaskState.RUNNING, finalAttemptStates,
        arg, jobHistoryEvents, 2L, 2L);
  }

};