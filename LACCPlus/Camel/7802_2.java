//,temp,AbstractOlingo2AppAPITestSupport.java,149,162,temp,Olingo4AppAPITest.java,728,741
//,2
public class xxx {
        @Override
        public void onResponse(T response, Map<String, String> responseHeaders) {
            this.response = response;
            if (LOG.isDebugEnabled()) {
                if (response instanceof ClientEntitySet) {
                    LOG.debug("Received response: {}", prettyPrint((ClientEntitySet) response));
                } else if (response instanceof ClientEntity) {
                    LOG.debug("Received response: {}", prettyPrint((ClientEntity) response));
                } else {
                    LOG.debug("Received response: {}", response);
                }
            }
            latch.countDown();
        }

};