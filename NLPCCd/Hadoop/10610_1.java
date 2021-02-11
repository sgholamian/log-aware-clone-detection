//,temp,sample_3373.java,2,16,temp,sample_3376.java,2,16
//,2
public class xxx {
public void dummy_method(){
Assert.assertEquals(0, errorsMap.size());
Assert.assertEquals(2, warningsMap.size());
Assert.assertTrue(warningsMap.containsKey("test message 1"));
Assert.assertTrue(warningsMap.containsKey("test message 2"));
Log4jWarningErrorMetricsAppender.Element msg1Info = warningsMap.get("test message 1");
Log4jWarningErrorMetricsAppender.Element msg2Info = warningsMap.get("test message 2");
Assert.assertEquals(2, msg1Info.count.intValue());
Assert.assertEquals(3, msg2Info.count.intValue());
Thread.sleep(1000);
cutoff.add(Time.now() / 1000);


log.info("test message");
}

};