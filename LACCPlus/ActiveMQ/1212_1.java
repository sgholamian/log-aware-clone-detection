//,temp,AmqpTransactionContext.java,171,209,temp,AmqpTransactionContext.java,126,164
//,2
public class xxx {
    public void rollback() throws Exception {
        if (transactionId == null) {
            throw new IllegalStateException("Rollback called with no active Transaction.");
        }

        preRollback();

        final ClientFuture request = new ClientFuture(new ClientFutureSynchronization() {

            @Override
            public void onPendingSuccess() {
                transactionId = null;
                postRollback();
            }

            @Override
            public void onPendingFailure(Throwable cause) {
                transactionId = null;
                postRollback();
            }
        });

        LOG.debug("Rollback on TX[{}] initiated", transactionId);
        session.getScheduler().execute(new Runnable() {

            @Override
            public void run() {
                try {
                    LOG.info("Attempting to roll back TX:[{}]", transactionId);
                    coordinator.discharge(transactionId, request, false);
                    session.pumpToProtonTransport(request);
                } catch (Exception e) {
                    request.onFailure(e);
                }
            }
        });

        request.sync();
    }

};