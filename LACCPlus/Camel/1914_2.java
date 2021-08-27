//,temp,StAXXPathSplitChoicePerformanceTest.java,65,83,temp,TokenPairIteratorSplitChoicePerformanceTest.java,59,77
//,3
public class xxx {
    @Test
    public void testTokenPairPerformanceRoute() throws Exception {
        NotifyBuilder notify = new NotifyBuilder(context).whenDone(size).create();

        boolean matches = notify.matches(5, TimeUnit.MINUTES);
        log.info("Processed file with {} elements in: {}", size, TimeUtils.printDuration(watch.taken()));

        log.info("Processed " + tiny.get() + " tiny messages");
        log.info("Processed " + small.get() + " small messages");
        log.info("Processed " + med.get() + " medium messages");
        log.info("Processed " + large.get() + " large messages");

        assertEquals((size / 10) * 4, tiny.get());
        assertEquals((size / 10) * 2, small.get());
        assertEquals((size / 10) * 3, med.get());
        assertEquals((size / 10) * 1, large.get());

        assertTrue(matches, "Should complete route");
    }

};