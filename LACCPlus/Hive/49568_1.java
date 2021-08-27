//,temp,HiveMetaStoreClient.java,4124,4161,temp,HiveMetaStoreClientPreCatalog.java,2691,2718
//,3
public class xxx {
  @InterfaceAudience.LimitedPrivate({"HCatalog"})
  @Override
  public NotificationEventResponse getNextNotification(long lastEventId, int maxEvents,
                                                       NotificationFilter filter) throws TException {
    NotificationEventRequest rqst = new NotificationEventRequest(lastEventId);
    rqst.setMaxEvents(maxEvents);
    NotificationEventResponse rsp = client.get_next_notification(rqst);
    LOG.debug("Got back {} events", rsp!= null ? rsp.getEventsSize() : 0);
    NotificationEventResponse filtered = new NotificationEventResponse();
    if (rsp != null && rsp.getEvents() != null) {
      long nextEventId = lastEventId + 1;
      long prevEventId = lastEventId;
      for (NotificationEvent e : rsp.getEvents()) {
        LOG.debug("Got event with id : {}", e.getEventId());
        if (e.getEventId() != nextEventId) {
          if (e.getEventId() == prevEventId) {
            LOG.error("NOTIFICATION_LOG table has multiple events with the same event Id {}. " +
                    "Something went wrong when inserting notification events.  Bootstrap the system " +
                    "again to get back teh consistent replicated state.", prevEventId);
            throw new IllegalStateException(REPL_EVENTS_WITH_DUPLICATE_ID_IN_METASTORE);
          } else {
            LOG.error("Requested events are found missing in NOTIFICATION_LOG table. Expected: {}, Actual: {}. "
                            + "Probably, cleaner would've cleaned it up. "
                            + "Try setting higher value for hive.metastore.event.db.listener.timetolive. "
                            + "Also, bootstrap the system again to get back the consistent replicated state.",
                    nextEventId, e.getEventId());
            throw new IllegalStateException(REPL_EVENTS_MISSING_IN_METASTORE);
          }
        }
        if ((filter != null) && filter.accept(e)) {
          filtered.addToEvents(e);
        }
        prevEventId = nextEventId;
        nextEventId++;
      }
    }
    return (filter != null) ? filtered : rsp;
  }

};