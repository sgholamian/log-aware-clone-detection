//,temp,KerberosAuthenticator.java,225,239,temp,NamedCommitterFactory.java,43,60
//,3
public class xxx {
  @SuppressWarnings("JavaReflectionMemberAccess")
  @Override
  public PathOutputCommitter createOutputCommitter(Path outputPath,
      TaskAttemptContext context) throws IOException {
    Class<? extends PathOutputCommitter> clazz = loadCommitterClass(context);
    LOG.debug("Using PathOutputCommitter implementation {}", clazz);
    try {
      Constructor<? extends PathOutputCommitter> ctor
          = clazz.getConstructor(Path.class, TaskAttemptContext.class);
      return ctor.newInstance(outputPath, context);
    } catch (NoSuchMethodException
        | InstantiationException
        | IllegalAccessException
        | InvocationTargetException e) {
      throw new IOException("Failed to create " + clazz
          + ":" + e, e);
    }
  }

};