//,temp,BoxCommentsManager.java,127,142,temp,BoxTasksManager.java,84,103
//,3
public class xxx {
    public BoxTask addFileTask(String fileId, BoxTask.Action action, Date dueAt, String message) {
        try {
            LOG.debug("Adding task to file(id={}) to '{}'", fileId, message);
            if (fileId == null) {
                throw new IllegalArgumentException("Parameter 'fileId' can not be null");
            }
            if (action == null) {
                throw new IllegalArgumentException("Parameter 'action' can not be null");
            }
            if (dueAt == null) {
                throw new IllegalArgumentException("Parameter 'dueAt' can not be null");
            }

            BoxFile fileToAddTaskOn = new BoxFile(boxConnection, fileId);
            return fileToAddTaskOn.addTask(action, message, dueAt).getResource();
        } catch (BoxAPIException e) {
            throw new RuntimeException(
                    String.format("Box API returned the error code %d%n%n%s", e.getResponseCode(), e.getResponse()), e);
        }
    }

};