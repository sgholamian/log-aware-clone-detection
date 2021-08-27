//,temp,WebHCatJTShim23.java,204,227,temp,KillQueryImpl.java,68,95
//,3
public class xxx {
  private Set<ApplicationId> getYarnChildJobs(String tag, long timestamp) {
    Set<ApplicationId> childYarnJobs = new HashSet<ApplicationId>();

    LOG.info(String.format("Querying RM for tag = %s, starting with ts = %s", tag, timestamp));

    GetApplicationsRequest gar = GetApplicationsRequest.newInstance();
    gar.setScope(ApplicationsRequestScope.OWN);
    gar.setStartRange(timestamp, System.currentTimeMillis());
    gar.setApplicationTags(Collections.singleton(tag));
    try {
      ApplicationClientProtocol proxy = ClientRMProxy.createRMProxy(conf,
          ApplicationClientProtocol.class);
      GetApplicationsResponse apps = proxy.getApplications(gar);
      List<ApplicationReport> appsList = apps.getApplicationList();
      for(ApplicationReport appReport : appsList) {
        childYarnJobs.add(appReport.getApplicationId());
      }
    } catch (IOException ioe) {
      throw new RuntimeException("Exception occurred while finding child jobs", ioe);
    } catch (YarnException ye) {
      throw new RuntimeException("Exception occurred while finding child jobs", ye);
    }
    return childYarnJobs;
  }

};