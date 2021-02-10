//,temp,CgroupsLCEResourcesHandler.java,287,300,temp,CGroupsHandlerImpl.java,321,334
//,2
public class xxx {
  private void logLineFromTasksFile(File cgf) {
    String str;
    if (LOG.isDebugEnabled()) {
      try (BufferedReader inl =
            new BufferedReader(new InputStreamReader(new FileInputStream(cgf
              + "/tasks"), "UTF-8"))) {
        if ((str = inl.readLine()) != null) {
          LOG.debug("First line in cgroup tasks file: " + cgf + " " + str);
        }
      } catch (IOException e) {
        LOG.warn("Failed to read cgroup tasks file. ", e);
      }
    }
  }

};