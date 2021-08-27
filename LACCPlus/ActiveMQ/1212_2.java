//,temp,AmqpTransactionContext.java,171,209,temp,AmqpTransactionContext.java,126,164
//,2
public class xxx {
    public void commit() throws Exception {
        if (transactionId == null) {
            throw new IllegalStateException("Commit called with no active Transaction.");
        }

        preCommit();

        final ClientFuture request = new ClientFuture(new ClientFutureSynchronization() {

            @Override
            public void onPendingSuccess() {
                transactionId = null;
                postCommit();
            }

            @Override
            public void onPendingFailure(Throwable cause) {
                transactionId = null;
                postCommit();
            }
        });

        LOG.debug("Commit on TX[{}] initiated", transactionId);
        session.getScheduler().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    LOG.info("Attempting to commit TX:[{}]", transactionId);
                    coordinator.discharge(transactionId, request, true);
                    session.pumpToProtonTransport(request);
                } catch (Exception e) {
                    request.onFailure(e);
                }
            }
        });

        request.sync();
    }

};