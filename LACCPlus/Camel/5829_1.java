//,temp,HeaderTesterImpl.java,90,110,temp,HeaderTesterImpl.java,69,88
//,3
public class xxx {
    public InoutHeaderResponse inoutHeader(InoutHeader me, Holder<SOAPHeaderData> headerInfo) {
        try {
            InoutHeaderResponse result = new InoutHeaderResponse();
            if (!relayHeaders) {
                if (headerInfo.value == null) {
                    result.setResponseType("pass");
                } else {
                    result.setResponseType("fail");
                }
            } else if (Constants.equals(Constants.IN_OUT_REQUEST_HEADER_DATA, headerInfo.value)) {
                result.setResponseType("pass");
            } else {
                result.setResponseType("fail");
            }
            headerInfo.value = Constants.IN_OUT_RESPONSE_HEADER_DATA;
            return result;
        } catch (Exception ex) {
            LOG.warn("Unexpected error: {}", ex.getMessage(), ex);
            throw new RuntimeException(ex);
        }
    }

};