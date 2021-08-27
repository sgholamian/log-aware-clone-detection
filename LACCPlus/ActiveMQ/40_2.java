//,temp,LoggingBrokerPlugin.java,298,304,temp,LoggingBrokerPlugin.java,198,204
//,3
public class xxx {
    @Override
    public void commitTransaction(ConnectionContext context, TransactionId xid, boolean onePhase) throws Exception {
        if (isLogAll() || isLogTransactionEvents()) {
            LOG.info("Committing transaction: {}", xid.getTransactionKey());
        }
        super.commitTransaction(context, xid, onePhase);
    }

};