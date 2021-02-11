//,temp,CgroupsLCEResourcesHandler.java,205,216,temp,CGroupsHandlerImpl.java,463,477
//,3
public class xxx {
  @Override
  public String createCGroup(CGroupController controller, String cGroupId)
      throws ResourceHandlerException {
    String path = getPathForCGroup(controller, cGroupId);

    if (LOG.isDebugEnabled()) {
      LOG.debug("createCgroup: " + path);
    }

    if (!new File(path).mkdir()) {
      throw new ResourceHandlerException("Failed to create cgroup at " + path);
    }

    return path;
  }

};