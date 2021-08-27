//,temp,ActiveMQSession.java,595,605,temp,ActiveMQSession.java,574,584
//,2
public class xxx {
    @Override
    public void commit() throws JMSException {
        checkClosed();
        if (!getTransacted()) {
            throw new javax.jms.IllegalStateException("Not a transacted session");
        }
        if (LOG.isDebugEnabled()) {
            LOG.debug(getSessionId() + " Transaction Commit :" + transactionContext.getTransactionId());
        }
        transactionContext.commit();
    }

};