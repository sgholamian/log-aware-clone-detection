//,temp,sample_402.java,2,11,temp,sample_6715.java,2,11
//,2
public class xxx {
public void testPerformance() {
SimpleUuidGenerator uuidGenerator = new SimpleUuidGenerator();
StopWatch watch = new StopWatch();
for (int i = 0; i < 500000; i++) {
uuidGenerator.generateUuid();
}


log.info("took");
}

};