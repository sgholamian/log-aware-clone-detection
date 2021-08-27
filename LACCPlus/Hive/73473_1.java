//,temp,HiveIcebergOutputCommitter.java,222,271,temp,HiveIcebergOutputCommitter.java,174,213
//,3
public class xxx {
  @Override
  public void abortJob(JobContext originalContext, int status) throws IOException {
    JobContext jobContext = TezUtil.enrichContextWithVertexId(originalContext);
    JobConf jobConf = jobContext.getJobConf();

    LOG.info("Job {} is aborted. Data file cleaning started", jobContext.getJobID());
    Collection<String> outputs = HiveIcebergStorageHandler.outputTables(jobContext.getJobConf());
    Collection<String> jobLocations = new ConcurrentLinkedQueue<>();

    ExecutorService fileExecutor = fileExecutor(jobConf);
    ExecutorService tableExecutor = tableExecutor(jobConf, outputs.size());
    try {
      // Cleans up the changes for the output tables in parallel
      Tasks.foreach(outputs)
          .suppressFailureWhenFinished()
          .executeWith(tableExecutor)
          .onFailure((output, exc) -> LOG.warn("Failed cleanup table {} on abort job", output, exc))
          .run(output -> {
            LOG.info("Cleaning job for jobID: {}, table: {}", jobContext.getJobID(), output);

            Table table = HiveIcebergStorageHandler.table(jobConf, output);
            String jobLocation = generateJobLocation(table.location(), jobConf, jobContext.getJobID());
            jobLocations.add(jobLocation);
            // list jobLocation to get number of forCommit files
            // we do this because map/reduce num in jobConf is unreliable and we have no access to vertex status info
            int numTasks = listForCommits(jobConf, jobLocation).size();
            Collection<DataFile> dataFiles =
                dataFiles(numTasks, fileExecutor, table.location(), jobContext, table.io(), false);

            // Check if we have files already committed and remove data files if there are any
            if (dataFiles.size() > 0) {
              Tasks.foreach(dataFiles)
                  .retry(3)
                  .suppressFailureWhenFinished()
                  .executeWith(fileExecutor)
                  .onFailure((file, exc) -> LOG.warn("Failed to remove data file {} on abort job", file.path(), exc))
                  .run(file -> table.io().deleteFile(file.path().toString()));
            }
          }, IOException.class);
    } finally {
      fileExecutor.shutdown();
      if (tableExecutor != null) {
        tableExecutor.shutdown();
      }
    }

    LOG.info("Job {} is aborted. Data file cleaning finished", jobContext.getJobID());

    cleanup(jobContext, jobLocations);
  }

};