//,temp,LocalFetcher.java,98,112,temp,JobControl.java,273,290
//,3
public class xxx {
  synchronized private void failAllJobs(Throwable t) {
    String message = "Unexpected System Error Occured: "+
    StringUtils.stringifyException(t);
    Iterator<ControlledJob> it = jobsInProgress.iterator();
    while(it.hasNext()) {
      ControlledJob j = it.next();
      try {
        j.failJob(message);
      } catch (IOException e) {
        LOG.error("Error while tyring to clean up "+j.getJobName(), e);
      } catch (InterruptedException e) {
        LOG.error("Error while tyring to clean up "+j.getJobName(), e);
      } finally {
        failedJobs.add(j);
        it.remove();
      }
    }
  }

};