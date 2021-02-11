//,temp,BaseAMRMProxyTest.java,242,259,temp,XmlCustomResourceTypeTestCase.java,88,94
//,3
public class xxx {
        @Override
        public R call() throws Exception {
          LOG.info("Sending request. Test context:"
              + testContext.toString());

          R response = null;
          try {
            response = func.invoke(testContext);
            LOG.info("Successfully sent request for context: "
                + testContext.toString());
          } catch (Throwable ex) {
            LOG.error("Failed to process request for context: "
                + testContext);
            response = null;
          }

          return response;
        }

};