//,temp,HttpInputStreamWithRelease.java,215,226,temp,SwiftNativeOutputStream.java,186,194
//,3
public class xxx {
  @Override
  protected void finalize() throws Throwable {
    if(!closed) {
      LOG.warn("stream not closed");
    }
    if (backupFile != null) {
      LOG.warn("Leaking backing file " + backupFile);
    }
  }

};