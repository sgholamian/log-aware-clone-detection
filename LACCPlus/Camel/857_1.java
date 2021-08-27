//,temp,BoxTasksManager.java,153,171,temp,BoxFilesManager.java,790,809
//,3
public class xxx {
    public BoxTask updateTaskInfo(String taskId, BoxTask.Info info) {
        try {
            LOG.debug("Updating info for task(id={})", taskId);
            if (taskId == null) {
                throw new IllegalArgumentException("Parameter 'taskId' can not be null");
            }
            if (info == null) {
                throw new IllegalArgumentException("Parameter 'info' can not be null");
            }

            BoxTask task = new BoxTask(boxConnection, taskId);
            task.updateInfo(info);

            return task;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};