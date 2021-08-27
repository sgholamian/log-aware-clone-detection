//,temp,BoxCollaborationsManager.java,57,69,temp,BoxTasksManager.java,179,194
//,2
public class xxx {
    public List<BoxTaskAssignment.Info> getTaskAssignments(String taskId) {
        try {
            LOG.debug("Getting assignments for task(id={})", taskId);
            if (taskId == null) {
                throw new IllegalArgumentException("Parameter 'taskId' can not be null");
            }

            BoxTask file = new BoxTask(boxConnection, taskId);

            return file.getAssignments();

        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};