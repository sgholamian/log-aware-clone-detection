//,temp,StorageManagerImpl.java,2343,2353,temp,StorageManagerImpl.java,1498,1508
//,3
public class xxx {
        @Override
        public void run() {
            try {
                s_logger.trace("Download URL Garbage Collection Thread is running.");

                cleanupDownloadUrls();

            } catch (Exception e) {
                s_logger.error("Caught the following Exception", e);
            }
        }

};