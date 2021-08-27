//,temp,JournalMessageStore.java,163,205,temp,JournalMessageStore.java,92,135
//,3
public class xxx {
    public void removeMessage(ConnectionContext context, final MessageAck ack) throws IOException {
        final boolean debug = LOG.isDebugEnabled();
        JournalQueueAck remove = new JournalQueueAck();
        remove.setDestination(destination);
        remove.setMessageAck(ack);

        final RecordLocation location = peristenceAdapter.writeCommand(remove, ack.isResponseRequired());
        if (!context.isInTransaction()) {
            if (debug) {
                LOG.debug("Journalled message remove for: " + ack.getLastMessageId() + ", at: " + location);
            }
            removeMessage(ack, location);
        } else {
            if (debug) {
                LOG.debug("Journalled transacted message remove for: " + ack.getLastMessageId() + ", at: " + location);
            }
            synchronized (this) {
                inFlightTxLocations.add(location);
            }
            transactionStore.removeMessage(this, ack, location);
            context.getTransaction().addSynchronization(new Synchronization() {
                public void afterCommit() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message remove commit for: " + ack.getLastMessageId() + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                        removeMessage(ack, location);
                    }
                }

                public void afterRollback() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message remove rollback for: " + ack.getLastMessageId() + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                    }
                }
            });

        }
    }

};