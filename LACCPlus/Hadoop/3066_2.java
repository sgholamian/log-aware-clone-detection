//,temp,FileSystemTimelineWriter.java,1019,1031,temp,TimelineCollector.java,217,225
//,3
public class xxx {
  public void putEntitiesAsync(TimelineEntities entities,
      UserGroupInformation callerUgi) throws IOException {
    if (LOG.isDebugEnabled()) {
      LOG.debug("putEntitiesAsync(entities=" + entities + ", callerUgi=" +
          callerUgi + ")");
    }

    writeTimelineEntities(entities, callerUgi);
  }

};