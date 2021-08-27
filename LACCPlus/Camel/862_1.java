//,temp,BoxTasksManager.java,203,223,temp,BoxTasksManager.java,130,144
//,3
public class xxx {
    @SuppressWarnings("unused") // compiler for some reason thinks 'if (assignTo
                               // == null)' clause is dead code.
    public BoxTask addAssignmentToTask(String taskId, BoxUser assignTo) {
        try {
            if (taskId == null) {
                throw new IllegalArgumentException("Parameter 'commentId' can not be null");
            }
            if (assignTo == null) {
                throw new IllegalArgumentException("Parameter 'assignTo' can not be null");
            }
            LOG.debug("Assigning task(id={}) to user(id={})", taskId, assignTo.getID());

            BoxTask task = new BoxTask(boxConnection, taskId);
            task.addAssignment(assignTo);

            return task;
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};