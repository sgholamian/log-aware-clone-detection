//,temp,AbstractOlingo2AppAPITestSupport.java,149,162,temp,Olingo4AppAPITest.java,728,741
//,2
public class xxx {
        @Override
        public void onResponse(T response, Map<String, String> responseHeaders) {
            this.response = response;
            if (LOG.isDebugEnabled()) {
                if (response instanceof ODataFeed) {
                    LOG.debug("Received response: {}", prettyPrint((ODataFeed)response));
                } else if (response instanceof ODataEntry) {
                    LOG.debug("Received response: {}", prettyPrint((ODataEntry)response));
                } else {
                    LOG.debug("Received response: {}", response);
                }
            }
            latch.countDown();
        }

};