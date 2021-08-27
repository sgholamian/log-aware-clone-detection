//,temp,GitProducer.java,633,642,temp,GitProducer.java,602,611
//,3
public class xxx {
    protected void doGc(Exchange exchange, String operation) throws Exception {
        Properties result = null;
        try {
            result = git.gc().call();
        } catch (Exception e) {
            LOG.error("There was an error in Git {} operation", operation);
            throw e;
        }
        updateExchange(exchange, result);
    }

};