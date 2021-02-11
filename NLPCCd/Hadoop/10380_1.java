//,temp,sample_6580.java,2,16,temp,sample_6582.java,2,16
//,3
public class xxx {
public void dummy_method(){
base = assertWebResponsesInRange(base, 2, 15);
getBandwidthGaugeUpdater().triggerUpdate(true);
long bytesWritten = AzureMetricsTestUtil.getCurrentBytesWritten(getInstrumentation());
assertTrue("The bytes written in the last second " + bytesWritten + " is pretty far from the expected range of around " + FILE_SIZE + " bytes plus a little overhead.", bytesWritten > (FILE_SIZE / 2) && bytesWritten < (FILE_SIZE * 2));
long totalBytesWritten = AzureMetricsTestUtil.getCurrentTotalBytesWritten(getInstrumentation());
assertTrue("The total bytes written  " + totalBytesWritten + " is pretty far from the expected range of around " + FILE_SIZE + " bytes plus a little overhead.", totalBytesWritten >= FILE_SIZE && totalBytesWritten < (FILE_SIZE * 2));
long uploadRate = AzureMetricsTestUtil.getLongGaugeValue(getInstrumentation(), WASB_UPLOAD_RATE);
long expectedRate = (FILE_SIZE * 1000L) / uploadDurationMs;
assertTrue("The upload rate " + uploadRate + " is below the expected range of around " + expectedRate + " bytes/second that the unit test observed. This should never be" + " the case since the test underestimates the rate by looking at " + " end-to-end time instead of just block upload time.", uploadRate >= expectedRate);
long uploadLatency = AzureMetricsTestUtil.getLongGaugeValue(getInstrumentation(), WASB_UPLOAD_LATENCY);


log.info("upload latency");
}

};