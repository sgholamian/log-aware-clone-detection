//,temp,StripedWriter.java,181,193,temp,StripedWriter.java,144,160
//,3
public class xxx {
  int initTargetStreams() {
    int nSuccess = 0;
    for (short i = 0; i < targets.length; i++) {
      try {
        writers[i] = createWriter(i);
        nSuccess++;
        targetsStatus[i] = true;
      } catch (Throwable e) {
        LOG.warn(e.getMessage());
      }
    }
    return nSuccess;
  }

};