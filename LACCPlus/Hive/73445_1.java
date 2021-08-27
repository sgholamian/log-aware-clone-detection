//,temp,HplSqlOperation.java,194,232,temp,SQLOperation.java,316,348
//,3
public class xxx {
    @Override
    public void run() {
      PrivilegedExceptionAction<Object> doAsAction = () -> {
        assert (!parentHive.allowClose());
        Hive.set(parentHive);
        SessionState.setCurrentSessionState(parentSessionState);
        PerfLogger.setPerfLogger(SessionState.getPerfLogger());
        LogUtils.registerLoggingContext(queryState.getConf());
        ShimLoader.getHadoopShims().setHadoopQueryContext(queryState.getQueryId());
        try {
          interpret();
        } catch (HiveSQLException e) {
          setOperationException(e);
          log.error("Error running hive query", e);
        } finally {
          LogUtils.unregisterLoggingContext();
          Hive.closeCurrent();
        }
        return null;
      };

      try {
        currentUGI.doAs(doAsAction);
      } catch (Exception e) {
        setOperationException(new HiveSQLException(e));
        log.error("Error running hive query as user : {}", currentUGI.getShortUserName(), e);
      } finally {
          /**
         * We'll cache the ThreadLocal RawStore object for this background thread for an orderly cleanup
         * when this thread is garbage collected later.
         * @see org.apache.hive.service.server.ThreadWithGarbageCleanup#finalize()
         */
        if (ThreadWithGarbageCleanup.currentThread() instanceof ThreadWithGarbageCleanup) {
          ThreadWithGarbageCleanup currentThread =
                  (ThreadWithGarbageCleanup) ThreadWithGarbageCleanup.currentThread();
          currentThread.cacheThreadLocalRawStore();
        }
      }
    }

};