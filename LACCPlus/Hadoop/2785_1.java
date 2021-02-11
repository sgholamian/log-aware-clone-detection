//,temp,TimelineReaderServer.java,99,117,temp,AbstractReservationSystem.java,246,270
//,3
public class xxx {
  private TimelineReader createTimelineReaderStore(final Configuration conf) {
    String timelineReaderClassName = conf.get(
        YarnConfiguration.TIMELINE_SERVICE_READER_CLASS,
        YarnConfiguration.DEFAULT_TIMELINE_SERVICE_READER_CLASS);
    LOG.info("Using store: " + timelineReaderClassName);
    try {
      Class<?> timelineReaderClazz = Class.forName(timelineReaderClassName);
      if (TimelineReader.class.isAssignableFrom(timelineReaderClazz)) {
        return (TimelineReader) ReflectionUtils.newInstance(
            timelineReaderClazz, conf);
      } else {
        throw new YarnRuntimeException("Class: " + timelineReaderClassName
            + " not instance of " + TimelineReader.class.getCanonicalName());
      }
    } catch (ClassNotFoundException e) {
      throw new YarnRuntimeException("Could not instantiate TimelineReader: "
          + timelineReaderClassName, e);
    }
  }

};