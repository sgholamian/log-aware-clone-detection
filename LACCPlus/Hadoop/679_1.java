//,temp,MRAppMaster.java,734,765,temp,DefaultSpeculator.java,119,151
//,3
public class xxx {
      public Speculator call(Configuration conf) {
        Class<? extends Speculator> speculatorClass;
        try {
          speculatorClass
              // "yarn.mapreduce.job.speculator.class"
              = conf.getClass(MRJobConfig.MR_AM_JOB_SPECULATOR,
                              DefaultSpeculator.class,
                              Speculator.class);
          Constructor<? extends Speculator> speculatorConstructor
              = speculatorClass.getConstructor
                   (Configuration.class, AppContext.class);
          Speculator result = speculatorConstructor.newInstance(conf, context);

          return result;
        } catch (InstantiationException ex) {
          LOG.error("Can't make a speculator -- check "
              + MRJobConfig.MR_AM_JOB_SPECULATOR, ex);
          throw new YarnRuntimeException(ex);
        } catch (IllegalAccessException ex) {
          LOG.error("Can't make a speculator -- check "
              + MRJobConfig.MR_AM_JOB_SPECULATOR, ex);
          throw new YarnRuntimeException(ex);
        } catch (InvocationTargetException ex) {
          LOG.error("Can't make a speculator -- check "
              + MRJobConfig.MR_AM_JOB_SPECULATOR, ex);
          throw new YarnRuntimeException(ex);
        } catch (NoSuchMethodException ex) {
          LOG.error("Can't make a speculator -- check "
              + MRJobConfig.MR_AM_JOB_SPECULATOR, ex);
          throw new YarnRuntimeException(ex);
        }
      }

};