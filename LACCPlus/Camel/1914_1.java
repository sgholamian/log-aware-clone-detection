//,temp,StAXXPathSplitChoicePerformanceTest.java,65,83,temp,TokenPairIteratorSplitChoicePerformanceTest.java,59,77
//,3
public class xxx {
    @Test
    public void testXPathSTaXPerformanceRoute() throws Exception {
        NotifyBuilder notify = new NotifyBuilder(context).whenDone(size).create();

        boolean matches = notify.matches(60, TimeUnit.SECONDS);
        log.info("Processed file with " + size + " elements in: " + TimeUtils.printDuration(watch.taken()));

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