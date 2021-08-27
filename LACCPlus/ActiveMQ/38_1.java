//,temp,LoggingBrokerPlugin.java,290,296,temp,LoggingBrokerPlugin.java,231,237
//,3
public class xxx {
    @Override
    public void beginTransaction(ConnectionContext context, TransactionId xid) throws Exception {
        if (isLogAll() || isLogTransactionEvents()) {
            LOG.info("Beginning transaction: {}", xid.getTransactionKey());
        }
        super.beginTransaction(context, xid);
    }

};