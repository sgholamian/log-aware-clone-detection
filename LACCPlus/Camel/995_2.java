//,temp,BoxFoldersManager.java,299,311,temp,BoxTasksManager.java,110,122
//,3
public class xxx {
    public void deleteTask(String taskId) {
        try {
            LOG.debug("Deleting task(id={})", taskId);
            if (taskId == null) {
                throw new IllegalArgumentException("Parameter 'taskId' can not be null");
            }
            BoxTask task = new BoxTask(boxConnection, taskId);
            task.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};