//,temp,JournalTopicMessageStore.java,121,130,temp,JournalMessageStore.java,124,132
//,3
public class xxx {
                public void afterRollback() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message add rollback for: " + id + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                    }
                    message.decrementReferenceCount();
                }

};