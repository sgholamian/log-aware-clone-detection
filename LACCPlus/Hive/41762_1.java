//,temp,ReExecDriver.java,217,225,temp,ReExecDriver.java,207,215
//,3
public class xxx {
  private boolean shouldReExecute() {
    boolean ret = false;
    for (IReExecutionPlugin p : plugins) {
      boolean shouldReExecute = p.shouldReExecute(executionIndex);
      LOG.debug("{}.shouldReExecute = {}", p, shouldReExecute);
      ret |= shouldReExecute;
    }
    return ret;
  }

};