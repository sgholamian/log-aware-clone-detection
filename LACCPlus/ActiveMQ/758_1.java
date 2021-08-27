//,temp,JournalTopicMessageStore.java,132,140,temp,JournalMessageStore.java,114,122
//,3
public class xxx {
                @Override
                public void afterRollback() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted acknowledge rollback for: " + messageId + ", at: " + location);
                    }
                    synchronized (JournalTopicMessageStore.this) {
                        inFlightTxLocations.remove(location);
                    }
                }

};