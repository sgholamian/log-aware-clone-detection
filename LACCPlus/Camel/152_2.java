//,temp,VanillaUuidGeneratorTest.java,42,54,temp,ShortUuidGeneratorTest.java,42,54
//,2
public class xxx {
    @Test
    public void testPerformance() {
        UuidGenerator uuidGenerator = new ShortUuidGenerator();
        StopWatch watch = new StopWatch();

        LOG.info("First id: " + uuidGenerator.generateUuid());
        for (int i = 0; i < 500000; i++) {
            uuidGenerator.generateUuid();
        }
        LOG.info("Last id:  " + uuidGenerator.generateUuid());

        LOG.info("Took " + TimeUtils.printDuration(watch.taken()));
    }

};