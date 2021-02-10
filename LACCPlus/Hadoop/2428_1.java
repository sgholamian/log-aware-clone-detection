//,temp,KeyValueBasedTimelineStore.java,463,489,temp,KeyValueBasedTimelineStore.java,281,322
//,3
public class xxx {
  public void put(TimelineDomain domain) throws IOException {
    if (getServiceStopped()) {
      LOG.info("Service stopped, return null for the storage");
      return;
    }
    TimelineDomain domainToReplace =
        domainById.get(domain.getId());
    Long currentTimestamp = System.currentTimeMillis();
    TimelineDomain domainToStore
        = KeyValueBasedTimelineStoreUtils.createTimelineDomain(
        domain.getId(), domain.getDescription(), domain.getOwner(),
        domain.getReaders(), domain.getWriters(),
        (domainToReplace == null ?
            currentTimestamp : domainToReplace.getCreatedTime()),
        currentTimestamp);
    domainById.put(domainToStore.getId(), domainToStore);
    Set<TimelineDomain> domainsByOneOwner =
        domainsByOwner.get(domainToStore.getOwner());
    if (domainsByOneOwner == null) {
      domainsByOneOwner = new HashSet<TimelineDomain>();
      domainsByOwner.put(domainToStore.getOwner(), domainsByOneOwner);
    }
    if (domainToReplace != null) {
      domainsByOneOwner.remove(domainToReplace);
    }
    domainsByOneOwner.add(domainToStore);
  }

};