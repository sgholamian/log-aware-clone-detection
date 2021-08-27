//,temp,LoggingBrokerPlugin.java,451,467,temp,LoggingBrokerPlugin.java,214,229
//,3
public class xxx {
    @Override
    public TransactionId[] getPreparedTransactions(ConnectionContext context) throws Exception {

        TransactionId[] result = super.getPreparedTransactions(context);
        if ((isLogAll() || isLogTransactionEvents()) && result != null) {
            StringBuffer tids = new StringBuffer();
            for (TransactionId tid : result) {
                if (tids.length() > 0) {
                    tids.append(", ");
                }
                tids.append(tid.getTransactionKey());
            }
            LOG.info("Prepared transactions: {}", tids);
        }
        return result;
    }

};