//,temp,AppController.java,275,303,temp,AppController.java,217,239
//,3
public class xxx {
  public void tasks() {
    try {
      requireJob();
    }
    catch (Exception e) {
      renderText(e.getMessage());
      return;
    }
    if (app.getJob() != null) {
      try {
        String tt = $(TASK_TYPE);
        tt = tt.isEmpty() ? "All" : StringUtils.capitalize(
            org.apache.hadoop.util.StringUtils.toLowerCase(
                MRApps.taskType(tt).toString()));
        setTitle(join(tt, " Tasks for ", $(JOB_ID)));
      } catch (Exception e) {
        LOG.error("Failed to render tasks page with task type : "
            + $(TASK_TYPE) + " for job id : " + $(JOB_ID), e);
        badRequest(e.getMessage());
      }
    }
    render(tasksPage());
  }

};