//,temp,LoggingBrokerPlugin.java,298,304,temp,LoggingBrokerPlugin.java,198,204
//,3
public class xxx {
    @Override
    public void forgetTransaction(ConnectionContext context, TransactionId transactionId) throws Exception {
        if (isLogAll() || isLogTransactionEvents()) {
            LOG.info("Forgetting transaction: {}", transactionId.getTransactionKey());
        }
        super.forgetTransaction(context, transactionId);
    }

};