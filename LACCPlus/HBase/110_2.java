//,temp,TestCacheOnWriteInSchema.java,188,212,temp,TestCompactionPolicy.java,116,134
//,3
public class xxx {
  @After
  public void tearDown() throws IOException {
    IOException ex = null;
    try {
      region.close();
    } catch (IOException e) {
      LOG.warn("Caught Exception", e);
      ex = e;
    }
    try {
      hlog.close();
    } catch (IOException e) {
      LOG.warn("Caught Exception", e);
      ex = e;
    }
    if (ex != null) {
      throw ex;
    }
  }

};