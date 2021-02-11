//,temp,NioTest.java,274,287,temp,NioTest.java,238,257
//,3
public class xxx {
            @Override
            public void doTask(final Task task) {
                if (task.getType() == Task.Type.CONNECT) {
                    LOGGER.info("Client: Received CONNECT task");
                    try {
                        LOGGER.info("Sending data to server");
                        task.getLink().send(getTestBytes());
                    } catch (ClosedChannelException e) {
                        LOGGER.error(e.getMessage());
                        e.printStackTrace();
                    }
                } else if (task.getType() == Task.Type.DATA) {
                    LOGGER.info("Client: Received DATA task");
                } else if (task.getType() == Task.Type.DISCONNECT) {
                    LOGGER.info("Client: Received DISCONNECT task");
                    stopClient();
                } else if (task.getType() == Task.Type.OTHER) {
                    LOGGER.info("Client: Received OTHER task");
                }
            }

};