//,temp,NNBench.java,895,923,temp,NNBench.java,857,887
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
            boolean result = filesystem.delete(filePath, true);
            if (!result) {
              throw new IOException("delete failed for " + filePath);
            }
            totalTimeAL1 += (System.currentTimeMillis() - startTimeAL);
            
            successfulOp = true;
            successfulFileOps ++;

            reporter.setStatus("Finish "+ l + " files");
          } catch (IOException e) {
            LOG.error("Exception recorded in op: Delete, " + "file: \""
                + filePath + "\"", e);
            numOfExceptions++;
          }
        }
      }
    }

};