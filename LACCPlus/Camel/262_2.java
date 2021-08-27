//,temp,ClassicUuidGeneratorTest.java,44,56,temp,SimpleUuidGeneratorTest.java,40,52
//,2
public class xxx {
    @Test
    public void testPerformance() {
        SimpleUuidGenerator uuidGenerator = new SimpleUuidGenerator();
        StopWatch watch = new StopWatch();

        LOG.info("First id: " + uuidGenerator.generateUuid());
        for (int i = 0; i < 500000; i++) {
            uuidGenerator.generateUuid();
        }
        LOG.info("Last id:  " + uuidGenerator.generateUuid());

        LOG.info("Took " + TimeUtils.printDuration(watch.taken()));
    }

};