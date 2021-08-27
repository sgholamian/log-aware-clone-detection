//,temp,WireTapShutdownRouteTest.java,86,95,temp,WireTapShutdownBeanTest.java,84,93
//,2
public class xxx {
        public void tapSomething(String body) throws Exception {
            try {
                EXCHANGER.exchange(null);
                Thread.sleep(100);
            } catch (Exception e) {
                fail("Should not be interrupted");
            }
            LOG.info("Wire tapping: {}", body);
            tapped = body;
        }

};