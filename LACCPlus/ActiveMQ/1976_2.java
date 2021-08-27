//,temp,JournalMessageStore.java,194,201,temp,JournalMessageStore.java,184,192
//,3
public class xxx {
                public void afterCommit() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message remove commit for: " + ack.getLastMessageId() + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                        removeMessage(ack, location);
                    }
                }

};