//,temp,PListStoreImpl.java,374,398,temp,LegacyJobSchedulerStoreImpl.java,285,301
//,3
public class xxx {
    @Override
    protected void doStop(ServiceStopper stopper) throws Exception {
        for (LegacyJobSchedulerImpl js : this.schedulers.values()) {
            js.stop();
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
        LOG.info(this + " stopped");
    }

};