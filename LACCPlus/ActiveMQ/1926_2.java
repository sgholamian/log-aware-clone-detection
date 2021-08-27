//,temp,KahaDBStore.java,1671,1682,temp,KahaDBStore.java,1570,1582
//,2
public class xxx {
        @Override
        public void aquireLocks() {
            if (this.locked.compareAndSet(false, true)) {
                try {
                    globalQueueSemaphore.acquire();
                    store.acquireLocalAsyncLock();
                    message.incrementReferenceCount();
                } catch (InterruptedException e) {
                    LOG.warn("Failed to aquire lock", e);
                }
            }

        }

};