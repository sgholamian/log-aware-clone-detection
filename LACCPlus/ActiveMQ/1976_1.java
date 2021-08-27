//,temp,JournalMessageStore.java,194,201,temp,JournalMessageStore.java,184,192
//,3
public class xxx {
                public void afterRollback() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message remove rollback for: " + ack.getLastMessageId() + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                    }
                }

};