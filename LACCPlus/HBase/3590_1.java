//,temp,TestStateMachineProcedure.java,227,242,temp,TestStateMachineProcedure.java,181,196
//,2
public class xxx {
    @Override
    protected Flow executeFromState(TestProcEnv env, TestSMProcedureState state) {
      LOG.info("EXEC " + state + " " + this);
      env.execCount.incrementAndGet();
      switch (state) {
        case STEP_1:
          if (!env.loop) {
            setNextState(TestSMProcedureState.STEP_2);
          }
          break;
        case STEP_2:
          addChildProcedure(new SimpleChildProcedure());
          return Flow.NO_MORE_STATE;
      }
      return Flow.HAS_MORE_STATE;
    }

};