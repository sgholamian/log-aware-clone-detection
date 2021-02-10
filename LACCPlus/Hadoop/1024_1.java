//,temp,NNBench.java,801,836,temp,NNBench.java,751,793
//,3
public class xxx {
    private void doOpenReadOp(String name,
                              Reporter reporter) {
      FSDataInputStream input;
      byte[] buffer = new byte[bytesToWrite];
      
      for (long l = 0l; l < numberOfFiles; l++) {
        Path filePath = new Path(new Path(baseDir, dataDirName), 
                name + "_" + l);

        boolean successfulOp = false;
        while (! successfulOp && numOfExceptions < MAX_OPERATION_EXCEPTIONS) {
          try {
            // Set up timer for measuring AL
            startTimeAL = System.currentTimeMillis();
            input = filesystem.open(filePath);
            totalTimeAL1 += (System.currentTimeMillis() - startTimeAL);
            
            // If the file needs to be read (specified at command line)
            if (readFile) {
              startTimeAL = System.currentTimeMillis();
              input.readFully(buffer);

              totalTimeAL2 += (System.currentTimeMillis() - startTimeAL);
            }
            input.close();
            successfulOp = true;
            successfulFileOps ++;

            reporter.setStatus("Finish "+ l + " files");
          } catch (IOException e) {
            LOG.info("Exception recorded in op: OpenRead " + e);
            numOfExceptions++;
          }
        }
      }
    }

};