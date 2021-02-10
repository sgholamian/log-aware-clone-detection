//,temp,TaskStatus.java,143,154,temp,TaskStatus.java,122,138
//,3
public class xxx {
  public void setDiagnosticInfo(String info) {
    // if the diag-info has already reached its max then log and return
    if (diagnosticInfo != null 
        && diagnosticInfo.length() == getMaxStringSize()) {
      LOG.info("task-diagnostic-info for task " + taskid + " : " + info);
      return;
    }
    diagnosticInfo = 
      ((diagnosticInfo == null) ? info : diagnosticInfo.concat(info)); 
    // trim the string to MAX_STRING_SIZE if needed
    if (diagnosticInfo != null 
        && diagnosticInfo.length() > getMaxStringSize()) {
      LOG.info("task-diagnostic-info for task " + taskid + " : " 
               + diagnosticInfo);
      diagnosticInfo = diagnosticInfo.substring(0, getMaxStringSize());
    }
  }

};