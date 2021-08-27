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
    LOG.debug("Got back " + rsp.getEventsSize() + " events");
    NotificationEventResponse filtered = new NotificationEventResponse();
    if (rsp != null && rsp.getEvents() != null) {
      long nextEventId = lastEventId + 1;
      for (NotificationEvent e : rsp.getEvents()) {
        if (e.getEventId() != nextEventId) {
          LOG.error("Requested events are found missing in NOTIFICATION_LOG table. Expected: {}, Actual: {}. "
                  + "Probably, cleaner would've cleaned it up. "
                  + "Try setting higher value for hive.metastore.event.db.listener.timetolive. "
                  + "Also, bootstrap the system again to get back the consistent replicated state.",
                  nextEventId, e.getEventId());
          throw new IllegalStateException("Notification events are missing.");
        }
        if ((filter != null) && filter.accept(e)) {
          filtered.addToEvents(e);
        }
        nextEventId++;
      }
    }
    return (filter != null) ? filtered : rsp;
  }

};