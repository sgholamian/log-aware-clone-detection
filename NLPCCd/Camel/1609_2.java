//,temp,sample_244.java,2,16,temp,sample_245.java,2,16
//,3
public class xxx {
public void dummy_method(){
if (!bodyMetadata) {
headers.put(SalesforceEndpointConfig.REPORT_METADATA, testReportMetadata);
body = testReportId;
bodyMetadata = true;
} else {
body = testReportMetadata;
bodyMetadata = false;
}
reportInstance = template().requestBodyAndHeaders("direct:executeAsyncReport", body, headers, ReportInstance.class);
assertNotNull("executeAsyncReport with metadata", reportInstance);


log.info("executeasyncreport with metadata");
}

};