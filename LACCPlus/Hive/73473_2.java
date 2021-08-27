//,temp,HiveIcebergOutputCommitter.java,222,271,temp,HiveIcebergOutputCommitter.java,174,213
//,3
public class xxx {
  @Override
  public void commitJob(JobContext originalContext) throws IOException {
    JobContext jobContext = TezUtil.enrichContextWithVertexId(originalContext);
    JobConf jobConf = jobContext.getJobConf();

    long startTime = System.currentTimeMillis();
    LOG.info("Committing job {} has started", jobContext.getJobID());

    Collection<String> outputs = HiveIcebergStorageHandler.outputTables(jobContext.getJobConf());
    Collection<String> jobLocations = new ConcurrentLinkedQueue<>();

    ExecutorService fileExecutor = fileExecutor(jobConf);
    ExecutorService tableExecutor = tableExecutor(jobConf, outputs.size());
    try {
      // Commits the changes for the output tables in parallel
      Tasks.foreach(outputs)
          .throwFailureWhenFinished()
          .stopOnFailure()
          .executeWith(tableExecutor)
          .run(output -> {
            Table table = HiveIcebergStorageHandler.table(jobConf, output);
            if (table != null) {
              String catalogName = HiveIcebergStorageHandler.catalogName(jobConf, output);
              jobLocations.add(generateJobLocation(table.location(), jobConf, jobContext.getJobID()));
              commitTable(table.io(), fileExecutor, jobContext, output, table.location(), catalogName);
            } else {
              LOG.info("CommitJob found no serialized table in config for table: {}. Skipping job commit.", output);
            }
          });
    } finally {
      fileExecutor.shutdown();
      if (tableExecutor != null) {
        tableExecutor.shutdown();
      }
    }

    LOG.info("Commit took {} ms for job {}", System.currentTimeMillis() - startTime, jobContext.getJobID());

    cleanup(jobContext, jobLocations);
  }

};