//,temp,DeploymentPlanningManagerImpl.java,852,867,temp,DeploymentPlanningManagerImpl.java,760,782
//,3
public class xxx {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        final PlannerHostReservationVO lockedEntry = _plannerHostReserveDao.lockRow(id, true);
                        if (lockedEntry == null) {
                            s_logger.error("Unable to lock the host entry for reservation, host: " + hostId);
                            return false;
                        }
                        // check before updating
                        if (lockedEntry.getResourceUsage() != null) {
                            lockedEntry.setResourceUsage(null);
                            _plannerHostReserveDao.persist(lockedEntry);
                            return true;
                        }

                        return false;
                    }

};