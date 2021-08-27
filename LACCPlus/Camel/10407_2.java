//,temp,FtpConsumer.java,66,93,temp,SftpConsumer.java,56,83
//,2
public class xxx {
    @Override
    protected void doStart() throws Exception {
        // turn off scheduler first, so autoCreate is handled before scheduler
        // starts
        boolean startScheduler = isStartScheduler();
        setStartScheduler(false);
        try {
            super.doStart();
            if (endpoint.isAutoCreate() && hasStartingDirectory()) {
                String dir = endpoint.getConfiguration().getDirectory();
                LOG.debug("Auto creating directory: {}", dir);
                try {
                    connectIfNecessary();
                    operations.buildDirectory(dir, true);
                } catch (GenericFileOperationFailedException e) {
                    // log a WARN as we want to start the consumer.
                    LOG.warn(
                            "Error auto creating directory: " + dir + " due " + e.getMessage() + ". This exception is ignored.",
                            e);
                }
            }
        } finally {
            if (startScheduler) {
                setStartScheduler(true);
                startScheduler();
            }
        }
    }

};