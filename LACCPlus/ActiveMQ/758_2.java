//,temp,JournalTopicMessageStore.java,132,140,temp,JournalMessageStore.java,114,122
//,3
public class xxx {
                public void afterCommit() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message add commit for: " + id + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                        addMessage(context, message, location);
                    }
                }

};