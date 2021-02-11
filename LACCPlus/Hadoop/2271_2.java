//,temp,CgroupsLCEResourcesHandler.java,301,325,temp,CGroupsHandlerImpl.java,528,555
//,3
public class xxx {
  @Override
  public void deleteCGroup(CGroupController controller, String cGroupId)
      throws ResourceHandlerException {
    boolean deleted = false;
    String cGroupPath = getPathForCGroup(controller, cGroupId);

    if (LOG.isDebugEnabled()) {
      LOG.debug("deleteCGroup: " + cGroupPath);
    }

    long start = clock.getTime();

    do {
      try {
        deleted = checkAndDeleteCgroup(new File(cGroupPath));
        if (!deleted) {
          Thread.sleep(deleteCGroupDelay);
        }
      } catch (InterruptedException ex) {
        // NOP
      }
    } while (!deleted && (clock.getTime() - start) < deleteCGroupTimeout);

    if (!deleted) {
      LOG.warn(String.format("Unable to delete  %s, tried to delete for %d ms",
          cGroupPath, deleteCGroupTimeout));
    }
  }

};