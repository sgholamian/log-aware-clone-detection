//,temp,sample_8390.java,2,11,temp,sample_4583.java,2,12
//,3
public class xxx {
public void tapSomething(String body) throws Exception {
try {
LATCH.await(5, TimeUnit.SECONDS);
Thread.sleep(100);
} catch (Exception e) {
fail("Should not be interrupted");
}


log.info("wire tapping");
}

};