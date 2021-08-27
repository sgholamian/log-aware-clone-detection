//,temp,sample_401.java,2,11,temp,sample_956.java,2,11
//,2
public class xxx {
public void testPerformance() {
DefaultUuidGenerator uuidGenerator = new DefaultUuidGenerator();
StopWatch watch = new StopWatch();
for (int i = 0; i < 500000; i++) {
uuidGenerator.generateUuid();
}


log.info("took");
}

};