//,temp,ActiveMQSession.java,595,605,temp,ActiveMQSession.java,574,584
//,2
public class xxx {
    @Override
    public void rollback() throws JMSException {
        checkClosed();
        if (!getTransacted()) {
            throw new javax.jms.IllegalStateException("Not a transacted session");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(getSessionId() + " Transaction Rollback, txid:"  + transactionContext.getTransactionId());
        }
        transactionContext.rollback();
    }

};