//,temp,ProjectManagerImpl.java,377,395,temp,SnapshotSchedulerImpl.java,408,421
//,3
public class xxx {
    @Override
    @DB
    public boolean removeSchedule(final Long volumeId, final Long policyId) {
        // We can only remove schedules which are in the future. Not which are already executed in the past.
        final SnapshotScheduleVO schedule = _snapshotScheduleDao.getCurrentSchedule(volumeId, policyId, false);
        boolean success = true;
        if (schedule != null) {
            success = _snapshotScheduleDao.remove(schedule.getId());
        }
        if (!success) {
            s_logger.debug("Error while deleting Snapshot schedule with Id: " + schedule.getId());
        }
        return success;
    }

};