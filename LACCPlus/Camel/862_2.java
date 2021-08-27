//,temp,BoxTasksManager.java,203,223,temp,BoxTasksManager.java,130,144
//,3
public class xxx {
    public BoxTask.Info getTaskInfo(String taskId) {
        try {
            LOG.debug("Getting info for task(id={})", taskId);
            if (taskId == null) {
                throw new IllegalArgumentException("Parameter 'taskId' can not be null");
            }

            BoxTask task = new BoxTask(boxConnection, taskId);

            return task.getInfo();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};