//,temp,sample_6580.java,2,16,temp,sample_6581.java,2,16
//,3
public class xxx {
public void dummy_method(){
long downloadDurationMs = new Date().getTime() - start.getTime();
assertEquals(FILE_SIZE, count);
logOpResponseCount("Reading a 1K file", base);
base = assertWebResponsesInRange(base, 1, 10);
getBandwidthGaugeUpdater().triggerUpdate(false);
long totalBytesRead = AzureMetricsTestUtil.getCurrentTotalBytesRead(getInstrumentation());
assertEquals(FILE_SIZE, totalBytesRead);
long bytesRead = AzureMetricsTestUtil.getCurrentBytesRead(getInstrumentation());
assertTrue("The bytes read in the last second " + bytesRead + " is pretty far from the expected range of around " + FILE_SIZE + " bytes plus a little overhead.", bytesRead > (FILE_SIZE / 2) && bytesRead < (FILE_SIZE * 2));
long downloadRate = AzureMetricsTestUtil.getLongGaugeValue(getInstrumentation(), WASB_DOWNLOAD_RATE);


log.info("download rate bytes second");
}

};