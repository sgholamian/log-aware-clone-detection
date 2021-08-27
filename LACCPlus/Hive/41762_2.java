//,temp,ReExecDriver.java,217,225,temp,ReExecDriver.java,207,215
//,3
public class xxx {
  private boolean shouldReExecuteAfterCompile(PlanMapper oldPlanMapper, PlanMapper newPlanMapper) {
    boolean ret = false;
    for (IReExecutionPlugin p : plugins) {
      boolean shouldReExecute = p.shouldReExecute(executionIndex, oldPlanMapper, newPlanMapper);
      LOG.debug("{}.shouldReExecuteAfterCompile = {}", p, shouldReExecute);
      ret |= shouldReExecute;
    }
    return ret;
  }

};