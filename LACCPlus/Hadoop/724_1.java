//,temp,AppController.java,275,303,temp,AppController.java,217,239
//,3
public class xxx {
  public void attempts() {
    try {
      requireJob();
    }
    catch (Exception e) {
      renderText(e.getMessage());
      return;
    }
    if (app.getJob() != null) {
      try {
        String taskType = $(TASK_TYPE);
        if (taskType.isEmpty()) {
          throw new RuntimeException("missing task-type.");
        }
        String attemptState = $(ATTEMPT_STATE);
        if (attemptState.isEmpty()) {
          throw new RuntimeException("missing attempt-state.");
        }
        setTitle(join(attemptState, " ",
            MRApps.taskType(taskType).toString(), " attempts in ", $(JOB_ID)));

        render(attemptsPage());
      } catch (Exception e) {
        LOG.error("Failed to render attempts page with task type : "
            + $(TASK_TYPE) + " for job id : " + $(JOB_ID), e);
        badRequest(e.getMessage());
      }
    }
  }

};