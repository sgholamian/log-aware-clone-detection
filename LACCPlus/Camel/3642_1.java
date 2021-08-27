//,temp,GetDataOperation.java,34,50,temp,DeleteOperation.java,38,53
//,3
public class xxx {
    @Override
    public OperationResult<byte[]> getResult() {
        try {
            Stat statistics = new Stat();

            if (LOG.isDebugEnabled()) {
                if (LOG.isTraceEnabled()) {
                    LOG.trace(format("Received data from '%s' path with statistics '%s'", node, statistics));
                } else {
                    LOG.debug(format("Received data from '%s' path ", node));
                }
            }
            return new OperationResult<>(connection.getData(node, true, statistics), statistics);
        } catch (Exception e) {
            return new OperationResult<>(e);
        }
    }

};