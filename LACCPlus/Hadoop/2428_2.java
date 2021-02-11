//,temp,KeyValueBasedTimelineStore.java,463,489,temp,KeyValueBasedTimelineStore.java,281,322
//,3
public class xxx {
  @Override
  public TimelineDomains getDomains(String owner)
      throws IOException {
    if (getServiceStopped()) {
      LOG.info("Service stopped, return null for the storage");
      return null;
    }
    List<TimelineDomain> domains = new ArrayList<TimelineDomain>();
    Set<TimelineDomain> domainsOfOneOwner = domainsByOwner.get(owner);
    if (domainsOfOneOwner == null) {
      return new TimelineDomains();
    }
    for (TimelineDomain domain : domainsByOwner.get(owner)) {
      TimelineDomain domainToReturn = KeyValueBasedTimelineStoreUtils
          .createTimelineDomain(
              domain.getId(),
              domain.getDescription(),
              domain.getOwner(),
              domain.getReaders(),
              domain.getWriters(),
              domain.getCreatedTime(),
              domain.getModifiedTime());
      domains.add(domainToReturn);
    }
    Collections.sort(domains, new Comparator<TimelineDomain>() {
      @Override
      public int compare(
          TimelineDomain domain1, TimelineDomain domain2) {
         int result = domain2.getCreatedTime().compareTo(
             domain1.getCreatedTime());
         if (result == 0) {
           return domain2.getModifiedTime().compareTo(
               domain1.getModifiedTime());
         } else {
           return result;
         }
      }
    });
    TimelineDomains domainsToReturn = new TimelineDomains();
    domainsToReturn.addDomains(domains);
    return domainsToReturn;
  }

};