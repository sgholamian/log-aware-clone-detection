//,temp,LeveldbTimelineStore.java,1783,1821,temp,RollingLevelDBTimelineStore.java,1768,1806
//,3
public class xxx {
  private static TimelineDomain getTimelineDomain(
      LeveldbIterator iterator, String domainId, byte[] prefix) throws IOException {
    // Iterate over all the rows whose key starts with prefix to retrieve the
    // domain information.
    TimelineDomain domain = new TimelineDomain();
    domain.setId(domainId);
    boolean noRows = true;
    for (; iterator.hasNext(); iterator.next()) {
      byte[] key = iterator.peekNext().getKey();
      if (!prefixMatches(prefix, prefix.length, key)) {
        break;
      }
      if (noRows) {
        noRows = false;
      }
      byte[] value = iterator.peekNext().getValue();
      if (value != null && value.length > 0) {
        if (key[prefix.length] == DESCRIPTION_COLUMN[0]) {
          domain.setDescription(new String(value, Charset.forName("UTF-8")));
        } else if (key[prefix.length] == OWNER_COLUMN[0]) {
          domain.setOwner(new String(value, Charset.forName("UTF-8")));
        } else if (key[prefix.length] == READER_COLUMN[0]) {
          domain.setReaders(new String(value, Charset.forName("UTF-8")));
        } else if (key[prefix.length] == WRITER_COLUMN[0]) {
          domain.setWriters(new String(value, Charset.forName("UTF-8")));
        } else if (key[prefix.length] == TIMESTAMP_COLUMN[0]) {
          domain.setCreatedTime(readReverseOrderedLong(value, 0));
          domain.setModifiedTime(readReverseOrderedLong(value, 8));
        } else {
          LOG.error("Unrecognized domain column: " + key[prefix.length]);
        }
      }
    }
    if (noRows) {
      return null;
    } else {
      return domain;
    }
  }    

};