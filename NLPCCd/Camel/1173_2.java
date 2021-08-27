//,temp,sample_241.java,2,14,temp,sample_242.java,2,16
//,3
public class xxx {
public void dummy_method(){
assertNotNull("query", reports);
final List<Report> reportsRecords = reports.getRecords();
assertFalse("Report not found", reportsRecords.isEmpty());
final String testReportId = reportsRecords.get(0).getId();
assertNotNull(testReportId);
final ReportDescription reportDescription = template().requestBody("direct:getReportDescription", testReportId, ReportDescription.class);
assertNotNull("getReportDescriptions", reportDescription);
final ReportMetadata testReportMetadata = reportDescription.getReportMetadata();
SyncReportResults reportResults = template().requestBodyAndHeader("direct:executeSyncReport", testReportId, SalesforceEndpointConfig.INCLUDE_DETAILS, Boolean.TRUE, SyncReportResults.class);
assertNotNull("executeSyncReport", reportResults);


log.info("executesyncreport");
}

};