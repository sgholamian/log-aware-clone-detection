//,temp,TestFilterFs.java,43,66,temp,TestHarFileSystem.java,334,364
//,3
public class xxx {
  public void testFilterFileSystem() throws Exception {
    for (Method m : AbstractFileSystem.class.getDeclaredMethods()) {
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
          FilterFs.class.getDeclaredMethod(m.getName(), m.getParameterTypes());
        }
        catch(NoSuchMethodException exc2){
          LOG.error("FilterFileSystem doesn't implement " + m);
          throw exc2;
        }
      }
    }
  }

};