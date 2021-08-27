//,temp,PListStoreImpl.java,374,398,temp,LegacyJobSchedulerStoreImpl.java,285,301
//,3
public class xxx {
    @Override
    protected synchronized void doStop(ServiceStopper stopper) throws Exception {
        if (scheduler != null) {
            if (PListStoreImpl.class.getSimpleName().equals(scheduler.getName())) {
                scheduler.stop();
                scheduler = null;
            }
        }
        for (PListImpl pl : this.persistentLists.values()) {
            pl.unload(null);
        }
        if (this.pageFile != null) {
            this.pageFile.unload();
        }
        if (this.journal != null) {
            journal.close();
        }
        if (this.lockFile != null) {
            this.lockFile.unlock();
        }
        this.lockFile = null;
        this.initialized = false;
        LOG.info(this + " stopped");

    }

};