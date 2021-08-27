//,temp,HeaderTesterImpl.java,90,110,temp,HeaderTesterImpl.java,69,88
//,3
public class xxx {
    public InHeaderResponse inHeader(InHeader me, SOAPHeaderData headerInfo) {
        try {
            InHeaderResponse result = new InHeaderResponse();
            if (!relayHeaders) {
                if (headerInfo == null) {
                    result.setResponseType("pass");
                } else {
                    result.setResponseType("fail");
                }
            } else if (Constants.equals(Constants.IN_HEADER_DATA, headerInfo)) {
                result.setResponseType("pass");
            } else {
                result.setResponseType("fail");
            }
            return result;
        } catch (Exception ex) {
            LOG.warn("Unexpected error: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

};