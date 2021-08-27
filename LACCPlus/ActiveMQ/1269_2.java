//,temp,AmqpTransactionContext.java,196,205,temp,AmqpTransactionContext.java,151,160
//,2
public class xxx {
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

};