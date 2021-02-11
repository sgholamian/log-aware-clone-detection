//,temp,NNBench.java,801,836,temp,NNBench.java,751,793
//,3
public class xxx {
    private void doCreateWriteOp(String name,
                                 Reporter reporter) {
      FSDataOutputStream out;
      byte[] buffer = new byte[bytesToWrite];
      
      for (long l = 0l; l < numberOfFiles; l++) {
        Path filePath = new Path(new Path(baseDir, dataDirName), 
                name + "_" + l);

        boolean successfulOp = false;
        while (! successfulOp && numOfExceptions < MAX_OPERATION_EXCEPTIONS) {
          try {
            // Set up timer for measuring AL (transaction #1)
            startTimeAL = System.currentTimeMillis();
            // Create the file
            // Use a buffer size of 512
            out = filesystem.create(filePath, 
                    true, 
                    512, 
                    replFactor, 
                    blkSize);
            out.write(buffer);
            totalTimeAL1 += (System.currentTimeMillis() - startTimeAL);

            // Close the file / file output stream
            // Set up timers for measuring AL (transaction #2)
            startTimeAL = System.currentTimeMillis();
            out.close();
            
            totalTimeAL2 += (System.currentTimeMillis() - startTimeAL);
            successfulOp = true;
            successfulFileOps ++;

            reporter.setStatus("Finish "+ l + " files");
          } catch (IOException e) {
            LOG.info("Exception recorded in op: " +
                    "Create/Write/Close");
 
            numOfExceptions++;
          }
        }
      }
    }

};