//,temp,ReencryptionStatus.java,92,97,temp,ReencryptionStatus.java,85,90
//,2
public class xxx {
  public void markZoneStarted(final Long zoneId) {
    final ZoneReencryptionStatus zs = zoneStatuses.get(zoneId);
    Preconditions.checkNotNull(zs, "Cannot find zone " + zoneId);
    LOG.info("Zone {} starts re-encryption processing", zoneId);
    zs.setState(State.Processing);
  }

};