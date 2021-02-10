//,temp,TestFilterFileSystem.java,215,239,temp,TestHarFileSystem.java,334,364
//,3
public class xxx {
  @Test
  public void testInheritedMethodsImplemented() throws Exception {
    int errors = 0;
    for (Method m : FileSystem.class.getDeclaredMethods()) {
      if (Modifier.isStatic(m.getModifiers()) ||
          Modifier.isPrivate(m.getModifiers()) ||
          Modifier.isFinal(m.getModifiers())) {
        continue;
      }

      try {
        MustNotImplement.class.getMethod(m.getName(), m.getParameterTypes());
        try {
          HarFileSystem.class.getDeclaredMethod(m.getName(), m.getParameterTypes());
          LOG.error("HarFileSystem MUST not implement " + m);
          errors++;
        } catch (NoSuchMethodException ex) {
          // Expected
        }
      } catch (NoSuchMethodException exc) {
        try {
          HarFileSystem.class.getDeclaredMethod(m.getName(), m.getParameterTypes());
        } catch (NoSuchMethodException exc2) {
          LOG.error("HarFileSystem MUST implement " + m);
          errors++;
        }
      }
    }
    assertTrue((errors + " methods were not overridden correctly - see log"),
        errors <= 0);
  }

};