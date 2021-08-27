//,temp,BoxGroupsManager.java,225,239,temp,BoxTasksManager.java,288,300
//,2
public class xxx {
    public void deleteTaskAssignment(String taskAssignmentId) {
        try {
            LOG.debug("Deleting task(id={})", taskAssignmentId);
            if (taskAssignmentId == null) {
                throw new IllegalArgumentException("Parameter 'taskAssignmentId' can not be null");
            }
            BoxTaskAssignment taskAssignment = new BoxTaskAssignment(boxConnection, taskAssignmentId);
            taskAssignment.delete();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};