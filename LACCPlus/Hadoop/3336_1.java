//,temp,ApplicationHistoryClientService.java,200,213,temp,ApplicationHistoryClientService.java,168,181
//,2
public class xxx {
  @Override
  public GetContainerReportResponse getContainerReport(
      GetContainerReportRequest request) throws YarnException, IOException {
    ContainerId containerId = request.getContainerId();
    try {
      GetContainerReportResponse response =
          GetContainerReportResponse.newInstance(history
            .getContainer(containerId));
      return response;
    } catch (IOException e) {
      LOG.error(e.getMessage(), e);
      throw e;
    }
  }

};