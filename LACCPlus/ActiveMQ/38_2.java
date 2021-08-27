//,temp,LoggingBrokerPlugin.java,290,296,temp,LoggingBrokerPlugin.java,231,237
//,3
public class xxx {
    @Override
    public int prepareTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        if (isLogAll() || isLogTransactionEvents()) {
            LOG.info("Preparing transaction: {}", xid.getTransactionKey());
        }
        return super.prepareTransaction(context, xid);
    }

};