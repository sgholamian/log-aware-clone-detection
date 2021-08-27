//,temp,sample_241.java,2,14,temp,sample_242.java,2,16
//,3
public class xxx {
public void testReport(String reportName) throws Exception {
final QueryRecordsReport reports = template().requestBody("direct:queryReport", "SELECT Id FROM Report WHERE DeveloperName='" + reportName + "'", QueryRecordsReport.class);
assertNotNull("query", reports);
final List<Report> reportsRecords = reports.getRecords();
assertFalse("Report not found", reportsRecords.isEmpty());
final String testReportId = reportsRecords.get(0).getId();
assertNotNull(testReportId);
final ReportDescription reportDescription = template().requestBody("direct:getReportDescription", testReportId, ReportDescription.class);
assertNotNull("getReportDescriptions", reportDescription);


log.info("getreportdescriptions");
}

};