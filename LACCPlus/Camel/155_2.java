//,temp,BoxCommentsManager.java,104,118,temp,BoxTasksManager.java,231,245
//,2
public class xxx {
    public BoxTaskAssignment.Info getTaskAssignmentInfo(String taskAssignmentId) {
        try {
            LOG.debug("Getting info for task(id={})", taskAssignmentId);
            if (taskAssignmentId == null) {
                throw new IllegalArgumentException("Parameter 'taskAssignmentId' can not be null");
            }

            BoxTaskAssignment taskAssignment = new BoxTaskAssignment(boxConnection, taskAssignmentId);

            return taskAssignment.getInfo();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};