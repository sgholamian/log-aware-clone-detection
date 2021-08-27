//,temp,JDBCPersistenceAdapter.java,174,194,temp,JDBCPersistenceAdapter.java,148,172
//,3
public class xxx {
    public void initSequenceIdGenerator() {
        TransactionContext c = null;
        try {
            c = getTransactionContext();
            getAdapter().doMessageIdScan(c, auditRecoveryDepth, new JDBCMessageIdScanListener() {
                @Override
                public void messageId(MessageId id) {
                    audit.isDuplicate(id);
                }
            });
        } catch (Exception e) {
            LOG.error("Failed to reload store message audit for JDBC persistence adapter", e);
        } finally {
            if (c != null) {
                try {
                    c.close();
                } catch (Throwable e) {
                }
            }
        }
    }

};