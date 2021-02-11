//,temp,StorageManagerImpl.java,2343,2353,temp,StorageManagerImpl.java,1498,1508
//,3
public class xxx {
        @Override
        protected void runInContext() {
            try {
                s_logger.trace("Storage Garbage Collection Thread is running.");

                cleanupStorage(true);

            } catch (Exception e) {
                s_logger.error("Caught the following Exception", e);
            }
        }

};