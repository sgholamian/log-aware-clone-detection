//,temp,TestFilterFs.java,43,66,temp,TestFilterFileSystem.java,215,239
//,3
public class xxx {
  @Test
  public void testFilterFileSystem() throws Exception {
    for (Method m : FileSystem.class.getDeclaredMethods()) {
      if (Modifier.isStatic(m.getModifiers()))
        continue;
      if (Modifier.isPrivate(m.getModifiers()))
        continue;
      if (Modifier.isFinal(m.getModifiers()))
        continue;
      
      try {
        DontCheck.class.getMethod(m.getName(), m.getParameterTypes());
        LOG.info("Skipping " + m);
      } catch (NoSuchMethodException exc) {
        LOG.info("Testing " + m);
        try{
          FilterFileSystem.class.getDeclaredMethod(m.getName(), m.getParameterTypes());
        }
        catch(NoSuchMethodException exc2){
          LOG.error("FilterFileSystem doesn't implement " + m);
          throw exc2;
        }
      }
    }
  }

};