//,temp,JournalTopicMessageStore.java,121,130,temp,JournalMessageStore.java,124,132
//,3
public class xxx {
                @Override
                public void afterCommit() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted acknowledge commit for: " + messageId + ", at: " + location);
                    }
                    synchronized (JournalTopicMessageStore.this) {
                        inFlightTxLocations.remove(location);
                        acknowledge(messageId, location, key);
                    }
                }

};