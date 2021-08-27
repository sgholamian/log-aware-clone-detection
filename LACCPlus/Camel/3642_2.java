//,temp,GetDataOperation.java,34,50,temp,DeleteOperation.java,38,53
//,3
public class xxx {
    @Override
    public OperationResult<Boolean> getResult() {
        try {
            connection.delete(node, version);
            if (LOG.isDebugEnabled()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace(format("Set data of node '%s'", node));
                } else {
                    LOG.debug(format("Set data of node '%s'", node));
                }
            }
            return new OperationResult<>(true, null, true);
        } catch (Exception e) {
            return new OperationResult<>(e);
        }
    }

};