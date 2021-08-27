//,temp,MemoryDecider.java,99,110,temp,SerializeFilter.java,83,94
//,2
public class xxx {
    private void evaluateWork(BaseWork w) throws SemanticException {

      if (w instanceof MapWork) {
        evaluateMapWork((MapWork) w);
      } else if (w instanceof ReduceWork) {
        evaluateReduceWork((ReduceWork) w);
      } else if (w instanceof MergeJoinWork) {
        evaluateMergeWork((MergeJoinWork) w);
      } else {
        LOG.info("We are not going to evaluate this work type: " + w.getClass().getCanonicalName());
      }
    }

};