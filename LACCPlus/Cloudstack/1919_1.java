//,temp,DeploymentPlanningManagerImpl.java,733,790,temp,ResourceManagerImpl.java,2939,2975
//,3
public class xxx {
    @DB
    private boolean checkIfHostFitsPlannerUsage(final long hostId, final PlannerResourceUsage resourceUsageRequired) {
        // TODO Auto-generated method stub
        // check if this host has been picked up by some other planner
        // exclusively
        // if planner can work with shared host, check if this host has
        // been marked as 'shared'
        // else if planner needs dedicated host,

        PlannerHostReservationVO reservationEntry = _plannerHostReserveDao.findByHostId(hostId);
        if (reservationEntry != null) {
            final long id = reservationEntry.getId();
            PlannerResourceUsage hostResourceType = reservationEntry.getResourceUsage();

            if (hostResourceType != null) {
                if (hostResourceType == resourceUsageRequired) {
                    return true;
                } else {
                    s_logger.debug("Cannot use this host for usage: " + resourceUsageRequired + ", since this host has been reserved for planner usage : " +
                            hostResourceType);
                    return false;
                }
            } else {
                final PlannerResourceUsage hostResourceTypeFinal = hostResourceType;
                // reserve the host for required resourceType
                // let us lock the reservation entry before updating.
                return Transaction.execute(new TransactionCallback<Boolean>() {
                    @Override
                    public Boolean doInTransaction(TransactionStatus status) {
                        final PlannerHostReservationVO lockedEntry = _plannerHostReserveDao.lockRow(id, true);
                        if (lockedEntry == null) {
                            s_logger.error("Unable to lock the host entry for reservation, host: " + hostId);
                            return false;
                        }
                        // check before updating
                        if (lockedEntry.getResourceUsage() == null) {
                            lockedEntry.setResourceUsage(resourceUsageRequired);
                            _plannerHostReserveDao.persist(lockedEntry);
                            return true;
                        } else {
                            // someone updated it earlier. check if we can still use it
                            if (lockedEntry.getResourceUsage() == resourceUsageRequired) {
                                return true;
                            } else {
                                s_logger.debug("Cannot use this host for usage: " + resourceUsageRequired + ", since this host has been reserved for planner usage : " +
                                        hostResourceTypeFinal);
                                return false;
                            }
                        }
                    }
                });

            }

        }

        return false;
    }

};