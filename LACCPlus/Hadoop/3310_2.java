//,temp,ReencryptionStatus.java,92,97,temp,ReencryptionStatus.java,85,90
//,2
public class xxx {
  public void markZoneForRetry(final Long zoneId) {
    final ZoneReencryptionStatus zs = zoneStatuses.get(zoneId);
    Preconditions.checkNotNull(zs, "Cannot find zone " + zoneId);
    LOG.info("Zone {} will retry re-encryption", zoneId);
    zs.setState(State.Submitted);
  }

};