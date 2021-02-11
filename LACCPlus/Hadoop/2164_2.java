//,temp,NNThroughputBenchmark.java,684,698,temp,NNThroughputBenchmark.java,580,595
//,2
public class xxx {
    @Override
    void generateInputs(int[] opsPerThread) throws IOException {
      assert opsPerThread.length == numThreads : "Error opsPerThread.length"; 
      clientProto.setSafeMode(HdfsConstants.SafeModeAction.SAFEMODE_LEAVE,
          false);
      // int generatedFileIdx = 0;
      LOG.info("Generate " + numOpsRequired + " intputs for " + getOpName());
      fileNames = new String[numThreads][];
      for(int idx=0; idx < numThreads; idx++) {
        int threadOps = opsPerThread[idx];
        fileNames[idx] = new String[threadOps];
        for(int jdx=0; jdx < threadOps; jdx++)
          fileNames[idx][jdx] = nameGenerator.
                                  getNextFileName("ThroughputBench");
      }
    }

};