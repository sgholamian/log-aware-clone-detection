//,temp,DeploymentPlanningManagerImpl.java,733,790,temp,ResourceManagerImpl.java,2939,2975
//,3
public class xxx {
    @Override
    @DB
    @ActionEvent(eventType = EventTypes.EVENT_HOST_RESERVATION_RELEASE, eventDescription = "releasing host reservation", async = true)
    public boolean releaseHostReservation(final Long hostId) {
        try {
            return Transaction.execute(new TransactionCallback<Boolean>() {
                @Override
                public Boolean doInTransaction(final TransactionStatus status) {
                    final PlannerHostReservationVO reservationEntry = _plannerHostReserveDao.findByHostId(hostId);
                    if (reservationEntry != null) {
                        final long id = reservationEntry.getId();
                        final PlannerHostReservationVO hostReservation = _plannerHostReserveDao.lockRow(id, true);
                        if (hostReservation == null) {
                            if (s_logger.isDebugEnabled()) {
                                s_logger.debug("Host reservation for host: " + hostId + " does not even exist.  Release reservartion call is ignored.");
                            }
                            return false;
                        }
                        hostReservation.setResourceUsage(null);
                        _plannerHostReserveDao.persist(hostReservation);
                        return true;
                    }

                    if (s_logger.isDebugEnabled()) {
                        s_logger.debug("Host reservation for host: " + hostId + " does not even exist.  Release reservartion call is ignored.");
                    }

                    return false;
                }
            });
        } catch (final CloudRuntimeException e) {
            throw e;
        } catch (final Throwable t) {
            s_logger.error("Unable to release host reservation for host: " + hostId, t);
            return false;
        }
    }

};