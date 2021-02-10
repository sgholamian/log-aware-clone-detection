//,temp,TimelineCollectorManager.java,74,92,temp,TimelineReaderServer.java,99,117
//,2
public class xxx {
  private TimelineWriter createTimelineWriter(final Configuration conf) {
    String timelineWriterClassName = conf.get(
        YarnConfiguration.TIMELINE_SERVICE_WRITER_CLASS,
            YarnConfiguration.DEFAULT_TIMELINE_SERVICE_WRITER_CLASS);
    LOG.info("Using TimelineWriter: " + timelineWriterClassName);
    try {
      Class<?> timelineWriterClazz = Class.forName(timelineWriterClassName);
      if (TimelineWriter.class.isAssignableFrom(timelineWriterClazz)) {
        return (TimelineWriter) ReflectionUtils.newInstance(
            timelineWriterClazz, conf);
      } else {
        throw new YarnRuntimeException("Class: " + timelineWriterClassName
            + " not instance of " + TimelineWriter.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException("Could not instantiate TimelineWriter: "
          + timelineWriterClassName, e);
    }
  }

};