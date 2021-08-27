//,temp,HplSqlOperation.java,194,232,temp,SQLOperation.java,316,348
//,3
public class xxx {
        @Override
        public Object run() throws HiveSQLException {
          assert (!parentHive.allowClose());
          Hive.set(parentHive);
          // TODO: can this result in cross-thread reuse of session state?
          SessionState.setCurrentSessionState(parentSessionState);
          PerfLogger.setPerfLogger(SessionState.getPerfLogger());
          if (!embedded) {
            LogUtils.registerLoggingContext(queryState.getConf());
          }
          ShimLoader.getHadoopShims().setHadoopQueryContext(queryState.getQueryId());

          try {
            if (asyncPrepare) {
              prepare(queryState);
            }
            runQuery();
          } catch (HiveSQLException e) {
            // TODO: why do we invent our own error path op top of the one from Future.get?
            setOperationException(e);
            log.error("Error running hive query", e);
          } finally {
            if (!embedded) {
              LogUtils.unregisterLoggingContext();
            }

            // If new hive object is created  by the child thread, then we need to close it as it might
            // have created a hms connection. Call Hive.closeCurrent() that closes the HMS connection, causes
            // HMS connection leaks otherwise.
            Hive.closeCurrent();
          }
          return null;
        }

};