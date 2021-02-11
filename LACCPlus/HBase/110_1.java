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
      walFactory.close();
    } catch (IOException e) {
      LOG.warn("Caught Exception", e);
      ex = e;
    }
    try {
      fs.delete(new Path(DIR), true);
    } catch (IOException e) {
      LOG.error("Could not delete " + DIR, e);
      ex = e;
    }
    if (ex != null) {
      throw ex;
    }
  }

};