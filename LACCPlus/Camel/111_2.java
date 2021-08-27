//,temp,ThrottlingExceptionRoutePolicyHalfOpenTest.java,117,123,temp,ThrottlingExceptionRoutePolicyHalfOpenHandlerTest.java,127,133
//,2
public class xxx {
    protected void sendMessage(String bodyText) {
        try {
            template.sendBody(url, bodyText);
        } catch (Exception e) {
            log.debug("Error sending:" + e.getCause().getMessage());
        }
    }

};