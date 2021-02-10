//,temp,LeveldbTimelineStore.java,404,489,temp,RollingLevelDBTimelineStore.java,444,542
//,3
public class xxx {
  private static TimelineEntity getEntity(String entityId, String entityType,
      Long startTime, EnumSet<Field> fields, DBIterator iterator,
      byte[] prefix, int prefixlen) throws IOException {
    if (fields == null) {
      fields = EnumSet.allOf(Field.class);
    }

    TimelineEntity entity = new TimelineEntity();
    boolean events = false;
    boolean lastEvent = false;
    if (fields.contains(Field.EVENTS)) {
      events = true;
    } else if (fields.contains(Field.LAST_EVENT_ONLY)) {
      lastEvent = true;
    } else {
      entity.setEvents(null);
    }
    boolean relatedEntities = false;
    if (fields.contains(Field.RELATED_ENTITIES)) {
      relatedEntities = true;
    } else {
      entity.setRelatedEntities(null);
    }
    boolean primaryFilters = false;
    if (fields.contains(Field.PRIMARY_FILTERS)) {
      primaryFilters = true;
    } else {
      entity.setPrimaryFilters(null);
    }
    boolean otherInfo = false;
    if (fields.contains(Field.OTHER_INFO)) {
      otherInfo = true;
    } else {
      entity.setOtherInfo(null);
    }

    // iterate through the entity's entry, parsing information if it is part
    // of a requested field
    for (; iterator.hasNext(); iterator.next()) {
      byte[] key = iterator.peekNext().getKey();
      if (!prefixMatches(prefix, prefixlen, key)) {
        break;
      }
      if (key.length == prefixlen) {
        continue;
      }
      if (key[prefixlen] == PRIMARY_FILTERS_COLUMN[0]) {
        if (primaryFilters) {
          addPrimaryFilter(entity, key, prefixlen
              + PRIMARY_FILTERS_COLUMN.length);
        }
      } else if (key[prefixlen] == OTHER_INFO_COLUMN[0]) {
        if (otherInfo) {
          Object o = null;
          String keyStr = parseRemainingKey(key,
              prefixlen + OTHER_INFO_COLUMN.length);
          try {
            o = fstConf.asObject(iterator.peekNext().getValue());
            entity.addOtherInfo(keyStr, o);
          } catch (Exception ignore) {
            try {
              // Fall back to 2.24 parser
              o = fstConf224.asObject(iterator.peekNext().getValue());
              entity.addOtherInfo(keyStr, o);
            } catch (Exception e) {
              LOG.warn("Error while decoding "
                  + entityId + ":otherInfo:" + keyStr, e);
            }
          }
        }
      } else if (key[prefixlen] == RELATED_ENTITIES_COLUMN[0]) {
        if (relatedEntities) {
          addRelatedEntity(entity, key, prefixlen
              + RELATED_ENTITIES_COLUMN.length);
        }
      } else if (key[prefixlen] == EVENTS_COLUMN[0]) {
        if (events || (lastEvent && entity.getEvents().size() == 0)) {
          TimelineEvent event = getEntityEvent(null, key, prefixlen
              + EVENTS_COLUMN.length, iterator.peekNext().getValue());
          if (event != null) {
            entity.addEvent(event);
          }
        }
      } else if (key[prefixlen] == DOMAIN_ID_COLUMN[0]) {
        byte[] v = iterator.peekNext().getValue();
        String domainId = new String(v, UTF_8);
        entity.setDomainId(domainId);
      } else {
        LOG.warn(String.format("Found unexpected column for entity %s of "
            + "type %s (0x%02x)", entityId, entityType, key[prefixlen]));
      }
    }

    entity.setEntityId(entityId);
    entity.setEntityType(entityType);
    entity.setStartTime(startTime);

    return entity;
  }

};