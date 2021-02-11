//,temp,ApplicationHistoryClientService.java,200,213,temp,ApplicationHistoryClientService.java,142,156
//,2
public class xxx {
  @Override
  public GetApplicationAttemptReportResponse getApplicationAttemptReport(
      GetApplicationAttemptReportRequest request) throws YarnException,
      IOException {
    ApplicationAttemptId appAttemptId = request.getApplicationAttemptId();
    try {
      GetApplicationAttemptReportResponse response =
          GetApplicationAttemptReportResponse.newInstance(history
            .getApplicationAttempt(appAttemptId));
      return response;
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      throw e;
    }
  }

};