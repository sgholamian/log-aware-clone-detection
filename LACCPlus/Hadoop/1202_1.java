//,temp,CgroupsLCEResourcesHandler.java,238,249,temp,CGroupsHandlerImpl.java,302,316
//,3
public class xxx {
  private void createCgroup(String controller, String groupName)
        throws IOException {
    String path = pathForCgroup(controller, groupName);

    if (LOG.isDebugEnabled()) {
      LOG.debug("createCgroup: " + path);
    }

    if (! new File(path).mkdir()) {
      throw new IOException("Failed to create cgroup at " + path);
    }
  }

};