//,temp,HeaderTesterImpl.java,143,153,temp,HeaderTesterImpl.java,127,141
//,3
public class xxx {
    public Me inoutOutOfBandHeader(Me me) {
        try {
            Me result = new Me();
            if (validateOutOfBandHander()) {
                addReplyOutOfBandHeader();
                result.setFirstName("pass");
            } else {
                result.setFirstName("fail");
            }
            return result;
        } catch (Exception ex) {
            LOG.warn("Unexpected error: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

};