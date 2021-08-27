//,temp,SedaRouteTest.java,96,104,temp,SedaRouteTest.java,61,69
//,2
public class xxx {
            public void configure() {
                from("seda:test.a").to("seda:test.b");
                from("seda:test.b").process(new Processor() {
                    public void process(Exchange e) {
                        log.debug("Received exchange: " + e.getIn());
                        latch.countDown();
                    }
                });
            }

};