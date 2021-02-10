//,temp,ApplicationHistoryClientService.java,200,213,temp,ApplicationHistoryClientService.java,168,181
//,2
public class xxx {
  @Override
  public GetApplicationReportResponse getApplicationReport(
      GetApplicationReportRequest request) throws YarnException, IOException {
    ApplicationId applicationId = request.getApplicationId();
    try {
      GetApplicationReportResponse response =
          GetApplicationReportResponse.newInstance(history
            .getApplication(applicationId));
      return response;
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      throw e;
    }
  }

};