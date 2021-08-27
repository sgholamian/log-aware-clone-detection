//,temp,WebHCatJTShim23.java,204,227,temp,KillQueryImpl.java,68,95
//,3
public class xxx {
  public static Set<ApplicationId> getChildYarnJobs(Configuration conf, String tag, String doAs, boolean doAsAdmin)
      throws IOException, YarnException {
    Set<ApplicationId> childYarnJobs = new HashSet<>();
    GetApplicationsRequest gar = GetApplicationsRequest.newInstance();
    gar.setScope(ApplicationsRequestScope.OWN);
    gar.setApplicationTags(Collections.singleton(tag));

    ApplicationClientProtocol proxy = ClientRMProxy.createRMProxy(conf, ApplicationClientProtocol.class);
    GetApplicationsResponse apps = proxy.getApplications(gar);
    List<ApplicationReport> appsList = apps.getApplicationList();
    for (ApplicationReport appReport : appsList) {
      if (doAsAdmin) {
        childYarnJobs.add(appReport.getApplicationId());
      } else if (StringUtils.isNotBlank(doAs)) {
        if (appReport.getApplicationTags().contains(QueryState.USERID_TAG + "=" + doAs)) {
          childYarnJobs.add(appReport.getApplicationId());
        }
      }
    }

    if (childYarnJobs.isEmpty()) {
      LOG.info("No child applications found");
    } else {
      LOG.info("Found child YARN applications: " + StringUtils.join(childYarnJobs, ","));
    }

    return childYarnJobs;
  }

};