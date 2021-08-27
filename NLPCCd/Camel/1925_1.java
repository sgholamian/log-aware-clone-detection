//,temp,sample_2510.java,2,11,temp,sample_2511.java,2,11
//,2
public class xxx {
public void testPerformance() {
JavaUuidGenerator uuidGenerator = new JavaUuidGenerator();
StopWatch watch = new StopWatch();
for (int i = 0; i < 500000; i++) {
uuidGenerator.generateUuid();
}


log.info("last id");
}

};