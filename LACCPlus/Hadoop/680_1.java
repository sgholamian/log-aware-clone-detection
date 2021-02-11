//,temp,NNBench.java,879,904,temp,NNBench.java,844,871
//,3
public class xxx {
    private void doDeleteOp(String name,
                            Reporter reporter) {
      for (long l = 0l; l < numberOfFiles; l++) {
        Path filePath = new Path(new Path(baseDir, dataDirName), 
                name + "_" + l);
        
        boolean successfulOp = false;
        while (! successfulOp && numOfExceptions < MAX_OPERATION_EXCEPTIONS) {
          try {
            // Set up timer for measuring AL
            startTimeAL = System.currentTimeMillis();
            filesystem.delete(filePath, true);
            totalTimeAL1 += (System.currentTimeMillis() - startTimeAL);
            
            successfulOp = true;
            successfulFileOps ++;

            reporter.setStatus("Finish "+ l + " files");
          } catch (IOException e) {
            LOG.info("Exception in recorded op: Delete");

            numOfExceptions++;
          }
        }
      }
    }

};