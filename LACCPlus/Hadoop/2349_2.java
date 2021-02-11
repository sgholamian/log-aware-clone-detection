//,temp,MRAppMaster.java,826,857,temp,DefaultSpeculator.java,121,153
//,3
public class xxx {
  static private TaskRuntimeEstimator getEstimator
      (Configuration conf, AppContext context) {
    TaskRuntimeEstimator estimator;
    
    try {
      // "yarn.mapreduce.job.task.runtime.estimator.class"
      Class<? extends TaskRuntimeEstimator> estimatorClass
          = conf.getClass(MRJobConfig.MR_AM_TASK_ESTIMATOR,
                          LegacyTaskRuntimeEstimator.class,
                          TaskRuntimeEstimator.class);

      Constructor<? extends TaskRuntimeEstimator> estimatorConstructor
          = estimatorClass.getConstructor();

      estimator = estimatorConstructor.newInstance();

      estimator.contextualize(conf, context);
    } catch (InstantiationException ex) {
      LOG.error("Can't make a speculation runtime estimator", ex);
      throw new YarnRuntimeException(ex);
    } catch (IllegalAccessException ex) {
      LOG.error("Can't make a speculation runtime estimator", ex);
      throw new YarnRuntimeException(ex);
    } catch (InvocationTargetException ex) {
      LOG.error("Can't make a speculation runtime estimator", ex);
      throw new YarnRuntimeException(ex);
    } catch (NoSuchMethodException ex) {
      LOG.error("Can't make a speculation runtime estimator", ex);
      throw new YarnRuntimeException(ex);
    }
    
  return estimator;
  }

};