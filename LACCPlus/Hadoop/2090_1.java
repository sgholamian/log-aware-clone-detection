//,temp,LeveldbTimelineStore.java,376,461,temp,RollingLevelDBTimelineStore.java,431,516
//,3
public class xxx {
  private static TimelineEntity getEntity(String entityId, String entityType,
      Long startTime, EnumSet<Field> fields, LeveldbIterator iterator,
      byte[] prefix, int prefixlen) throws IOException {
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
          addPrimaryFilter(entity, key,
              prefixlen + PRIMARY_FILTERS_COLUMN.length);
        }
      } else if (key[prefixlen] == OTHER_INFO_COLUMN[0]) {
        if (otherInfo) {
          entity.addOtherInfo(parseRemainingKey(key,
              prefixlen + OTHER_INFO_COLUMN.length),
              GenericObjectMapper.read(iterator.peekNext().getValue()));
        }
      } else if (key[prefixlen] == RELATED_ENTITIES_COLUMN[0]) {
        if (relatedEntities) {
          addRelatedEntity(entity, key,
              prefixlen + RELATED_ENTITIES_COLUMN.length);
        }
      } else if (key[prefixlen] == EVENTS_COLUMN[0]) {
        if (events || (lastEvent &&
            entity.getEvents().size() == 0)) {
          TimelineEvent event = getEntityEvent(null, key, prefixlen +
              EVENTS_COLUMN.length, iterator.peekNext().getValue());
          if (event != null) {
            entity.addEvent(event);
          }
        }
      } else if (key[prefixlen] == DOMAIN_ID_COLUMN[0]) {
        byte[] v = iterator.peekNext().getValue();
        String domainId = new String(v, Charset.forName("UTF-8"));
        entity.setDomainId(domainId);
      } else {
        if (key[prefixlen] !=
            INVISIBLE_REVERSE_RELATED_ENTITIES_COLUMN[0]) {
          LOG.warn(String.format("Found unexpected column for entity %s of " +
              "type %s (0x%02x)", entityId, entityType, key[prefixlen]));
        }
      }
    }

    entity.setEntityId(entityId);
    entity.setEntityType(entityType);
    entity.setStartTime(startTime);

    return entity;
  }

};