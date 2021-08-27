//,temp,BeanVsProcessorPerformanceTest.java,53,63,temp,PrimitiveTypeConverterIssueTest.java,27,35
//,3
public class xxx {
    @Test
    public void testBean() throws Exception {
        StopWatch watch = new StopWatch();

        for (int i = 0; i < size; i++) {
            Object out = template.requestBody("direct:b", "" + i);
            assertEquals("Bye " + i, out);
        }

        log.info("Bean took {} ms ", watch.taken());
    }

};