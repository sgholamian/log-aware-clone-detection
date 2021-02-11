//,temp,HighAvailabilityManagerImpl.java,652,660,temp,HighAvailabilityManagerImpl.java,289,304
//,3
public class xxx {
    @Override
    public void scheduleStop(VMInstanceVO vm, long hostId, WorkType type) {
        assert (type == WorkType.CheckStop || type == WorkType.ForceStop || type == WorkType.Stop);

        if (_haDao.hasBeenScheduled(vm.getId(), type)) {
            s_logger.info("There's already a job scheduled to stop " + vm);
            return;
        }

        HaWorkVO work = new HaWorkVO(vm.getId(), vm.getType(), type, Step.Scheduled, hostId, vm.getState(), 0, vm.getUpdated());
        _haDao.persist(work);
        if (s_logger.isDebugEnabled()) {
            s_logger.debug("Scheduled " + work);
        }
        wakeupWorkers();
    }

};