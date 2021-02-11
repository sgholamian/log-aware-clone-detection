//,temp,TaskStatus.java,143,154,temp,TaskStatus.java,122,138
//,3
public class xxx {
  public void setStateString(String stateString) {
    if (stateString != null) {
      if (stateString.length() <= getMaxStringSize()) {
        this.stateString = stateString;
      } else {
        // log it
        LOG.info("state-string for task " + taskid + " : " + stateString);
        // trim the state string
        this.stateString = stateString.substring(0, getMaxStringSize());
      }
    }
  }

};