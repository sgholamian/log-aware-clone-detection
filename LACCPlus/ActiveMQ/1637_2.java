//,temp,JournalMessageStore.java,163,205,temp,JournalMessageStore.java,92,135
//,3
public class xxx {
    public void addMessage(final ConnectionContext context, final Message message) throws IOException {

        final MessageId id = message.getMessageId();

        final boolean debug = LOG.isDebugEnabled();
        message.incrementReferenceCount();

        final RecordLocation location = peristenceAdapter.writeCommand(message, message.isResponseRequired());
        if (!context.isInTransaction()) {
            if (debug) {
                LOG.debug("Journalled message add for: " + id + ", at: " + location);
            }
            addMessage(context, message, location);
        } else {
            if (debug) {
                LOG.debug("Journalled transacted message add for: " + id + ", at: " + location);
            }
            synchronized (this) {
                inFlightTxLocations.add(location);
            }
            transactionStore.addMessage(this, message, location);
            context.getTransaction().addSynchronization(new Synchronization() {
                public void afterCommit() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message add commit for: " + id + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                        addMessage(context, message, location);
                    }
                }

                public void afterRollback() throws Exception {
                    if (debug) {
                        LOG.debug("Transacted message add rollback for: " + id + ", at: " + location);
                    }
                    synchronized (JournalMessageStore.this) {
                        inFlightTxLocations.remove(location);
                    }
                    message.decrementReferenceCount();
                }
            });
        }
    }

};