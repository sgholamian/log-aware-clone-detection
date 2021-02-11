//,temp,DatabaseIntegrityChecker.java,269,294,temp,UploadMonitorImpl.java,450,469
//,3
public class xxx {
        @Override
        protected void runInContext() {
            try {
                GlobalLock scanLock = GlobalLock.getInternLock("uploadmonitor.storageGC");
                try {
                    if (scanLock.lock(3)) {
                        try {
                            cleanupStorage();
                        } finally {
                            scanLock.unlock();
                        }
                    }
                } finally {
                    scanLock.releaseRef();
                }

            } catch (Exception e) {
                s_logger.error("Caught the following Exception", e);
            }
        }

};