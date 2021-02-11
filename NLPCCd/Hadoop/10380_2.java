//,temp,sample_6580.java,2,16,temp,sample_6582.java,2,16
//,3
public class xxx {
public void dummy_method(){
base = assertWebResponsesInRange(base, 1, 10);
getBandwidthGaugeUpdater().triggerUpdate(false);
long totalBytesRead = AzureMetricsTestUtil.getCurrentTotalBytesRead(getInstrumentation());
assertEquals(FILE_SIZE, totalBytesRead);
long bytesRead = AzureMetricsTestUtil.getCurrentBytesRead(getInstrumentation());
assertTrue("The bytes read in the last second " + bytesRead + " is pretty far from the expected range of around " + FILE_SIZE + " bytes plus a little overhead.", bytesRead > (FILE_SIZE / 2) && bytesRead < (FILE_SIZE * 2));
long downloadRate = AzureMetricsTestUtil.getLongGaugeValue(getInstrumentation(), WASB_DOWNLOAD_RATE);
expectedRate = (FILE_SIZE * 1000L) / downloadDurationMs;
assertTrue("The download rate " + downloadRate + " is below the expected range of around " + expectedRate + " bytes/second that the unit test observed. This should never be" + " the case since the test underestimates the rate by looking at " + " end-to-end time instead of just block download time.", downloadRate >= expectedRate);
long downloadLatency = AzureMetricsTestUtil.getLongGaugeValue(getInstrumentation(), WASB_DOWNLOAD_LATENCY);


log.info("download latency");
}

};