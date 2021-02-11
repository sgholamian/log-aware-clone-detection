//,temp,HttpInputStreamWithRelease.java,215,226,temp,SwiftNativeOutputStream.java,186,194
//,3
public class xxx {
  @Override
  protected void finalize() {
    try {
      if (release("finalize()", constructionStack)) {
        LOG.warn("input stream of {}" +
                 " not closed properly -cleaned up in finalize()", uri);
      }
    } catch (Exception e) {
      //swallow anything that failed here
      LOG.warn("Exception while releasing {} in finalizer", uri, e);
    }
  }

};