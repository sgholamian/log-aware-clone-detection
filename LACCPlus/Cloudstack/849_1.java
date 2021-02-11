//,temp,NioTest.java,274,287,temp,NioTest.java,238,257
//,3
public class xxx {
            @Override
            public void doTask(final Task task) {
                if (task.getType() == Task.Type.CONNECT) {
                    LOGGER.info("Server: Received CONNECT task");
                } else if (task.getType() == Task.Type.DATA) {
                    LOGGER.info("Server: Received DATA task");
                    doServerProcess(task.getData());
                } else if (task.getType() == Task.Type.DISCONNECT) {
                    LOGGER.info("Server: Received DISCONNECT task");
                    stopServer();
                } else if (task.getType() == Task.Type.OTHER) {
                    LOGGER.info("Server: Received OTHER task");
                }
            }

};