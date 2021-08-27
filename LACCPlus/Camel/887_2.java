//,temp,BeanVsProcessorPerformanceTest.java,53,63,temp,PrimitiveTypeConverterIssueTest.java,27,35
//,3
public class xxx {
    @Test
    public void testPrimitiveTypeConverter() throws Exception {
        StopWatch watch = new StopWatch();
        for (int i = 0; i < 10000; i++) {
            int num = context.getTypeConverter().convertTo(int.class, "123");
            assertEquals(123, num);
        }
        log.info("Time taken: " + watch.taken());
    }

};