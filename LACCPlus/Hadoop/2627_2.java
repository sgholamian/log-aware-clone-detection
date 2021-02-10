//,temp,SecurityUtil.java,422,432,temp,CGroupsHandlerImpl.java,482,496
//,3
public class xxx {
  private void logLineFromTasksFile(File cgf) {
    String str;
    if (LOG.isDebugEnabled()) {
      try (BufferedReader inl =
          new BufferedReader(new InputStreamReader(new FileInputStream(cgf
              + "/tasks"), "UTF-8"))) {
        str = inl.readLine();
        if (str != null) {
          LOG.debug("First line in cgroup tasks file: " + cgf + " " + str);
        }
      } catch (IOException e) {
        LOG.warn("Failed to read cgroup tasks file. ", e);
      }
    }
  }

};