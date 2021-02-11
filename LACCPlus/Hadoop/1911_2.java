//,temp,CgroupsLCEResourcesHandler.java,308,331,temp,CGroupsHandlerImpl.java,342,364
//,3
public class xxx {
  boolean checkAndDeleteCgroup(File cgf) throws InterruptedException {
    boolean deleted = false;
    // FileInputStream in = null;
    try (FileInputStream in = new FileInputStream(cgf + "/tasks")) {
      if (in.read() == -1) {
        /*
         * "tasks" file is empty, sleep a bit more and then try to delete the
         * cgroup. Some versions of linux will occasionally panic due to a race
         * condition in this area, hence the paranoia.
         */
        Thread.sleep(deleteCGroupDelay);
        deleted = cgf.delete();
        if (!deleted) {
          LOG.warn("Failed attempt to delete cgroup: " + cgf);
        }
      } else {
        logLineFromTasksFile(cgf);
      }
    } catch (IOException e) {
      LOG.warn("Failed to read cgroup tasks file. ", e);
    }
    return deleted;
  }

};