//,temp,ReplLoadWork.java,199,236,temp,ReplDumpWork.java,195,231
//,3
public class xxx {
  public List<Task<?>> externalTableCopyTasks(TaskTracker tracker, HiveConf conf) throws IOException {
    if (conf.getBoolVar(HiveConf.ConfVars.REPL_DUMP_SKIP_IMMUTABLE_DATA_COPY)) {
      return Collections.emptyList();
    }
    List<Task<?>> tasks = new ArrayList<>();
    Retryable retryable = Retryable.builder()
            .withHiveConf(conf)
            .withRetryOnException(UncheckedIOException.class).build();
    try {
      retryable.executeCallable((Callable<Void>) ()-> {
        try{
          int numEntriesToSkip = tasks == null ? 0 : tasks.size();
          while (externalTblCopyPathIterator.hasNext() &&  tracker.canAddMoreTasks()) {
            if(numEntriesToSkip > 0) {
              //skip tasks added in previous attempts of this retryable block
              externalTblCopyPathIterator.next();
              numEntriesToSkip--;
              continue;
            }
            DirCopyWork dirCopyWork = new DirCopyWork(metricCollector, currentDumpPath.toString());
            dirCopyWork.loadFromString(externalTblCopyPathIterator.next());
            Task<DirCopyWork> task = TaskFactory.get(dirCopyWork, conf);
            tasks.add(task);
            tracker.addTask(task);
            LOG.debug("added task for {}", dirCopyWork);
          }
        } catch (UncheckedIOException e) {
          LOG.error("Reading entry for data copy failed for external tables, attempting retry.", e);
          throw e;
        }
        return null;
      });
    } catch (Exception e) {
      throw new IOException(ErrorMsg.REPL_RETRY_EXHAUSTED.format(e.getMessage()));
    }
    return tasks;
  }

};