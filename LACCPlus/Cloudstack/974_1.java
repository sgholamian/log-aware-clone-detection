//,temp,HighAvailabilityManagerImpl.java,652,660,temp,HighAvailabilityManagerImpl.java,289,304
//,3
public class xxx {
    @Override
    public void scheduleDestroy(VMInstanceVO vm, long hostId) {
        final HaWorkVO work = new HaWorkVO(vm.getId(), vm.getType(), WorkType.Destroy, Step.Scheduled, hostId, vm.getState(), 0, vm.getUpdated());
        _haDao.persist(work);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Scheduled " + work.toString());
        }
        wakeupWorkers();
    }

};