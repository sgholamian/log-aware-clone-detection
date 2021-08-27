//,temp,CxfRsProducer.java,816,828,temp,CxfRsProducer.java,723,735
//,2
public class xxx {
        @Override
        public void failed(Throwable throwable) {
            LOG.error("Failed request ", throwable);
            try {
                // handle cookies
                saveCookies(exchange, client, cxfRsEndpoint.getCookieHandler());
                fail(throwable);
            } catch (Exception error) {
                LOG.error("Error while processing failed request", error);
            } finally {
                callback.done(false);
            }
        }

};