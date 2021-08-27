//,temp,sample_244.java,2,16,temp,sample_245.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (!bodyMetadata) {
headers.put(SalesforceEndpointConfig.REPORT_METADATA, testReportMetadata);
body = testReportId;
} else {
body = testReportMetadata;
}
reportResults = template().requestBodyAndHeaders("direct:executeSyncReport", body, headers, SyncReportResults.class);
assertNotNull("executeSyncReport with metadata", reportResults);
ReportInstance reportInstance = template().requestBodyAndHeader("direct:executeAsyncReport", testReportId, SalesforceEndpointConfig.INCLUDE_DETAILS, true, ReportInstance.class);
assertNotNull("executeAsyncReport", reportInstance);


log.info("executeasyncreport");
}

};