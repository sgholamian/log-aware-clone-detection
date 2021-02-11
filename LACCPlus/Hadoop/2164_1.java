//,temp,NNThroughputBenchmark.java,684,698,temp,NNThroughputBenchmark.java,580,595
//,2
public class xxx {
    @Override
    void generateInputs(int[] opsPerThread) throws IOException {
      assert opsPerThread.length == numThreads : "Error opsPerThread.length";
      clientProto.setSafeMode(HdfsConstants.SafeModeAction.SAFEMODE_LEAVE,
          false);
      LOG.info("Generate " + numOpsRequired + " inputs for " + getOpName());
      dirPaths = new String[numThreads][];
      for(int idx=0; idx < numThreads; idx++) {
        int threadOps = opsPerThread[idx];
        dirPaths[idx] = new String[threadOps];
        for(int jdx=0; jdx < threadOps; jdx++)
          dirPaths[idx][jdx] = nameGenerator.
              getNextFileName("ThroughputBench");
      }
    }

};