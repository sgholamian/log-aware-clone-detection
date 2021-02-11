//,temp,InMemoryStore.java,123,135,temp,MemoryPlacementConstraintManager.java,120,138
//,3
public class xxx {
  @Override
  public void addConstraint(ApplicationId appId, Set<String> sourceTags,
      PlacementConstraint placementConstraint, boolean replace) {
    try {
      writeLock.lock();
      Map<String, PlacementConstraint> constraintsForApp =
          appConstraints.get(appId);
      if (constraintsForApp == null) {
        LOG.info("Cannot add constraint to application {}, as it has not "
            + "been registered yet.", appId);
        return;
      }

      addConstraintToMap(constraintsForApp, sourceTags, placementConstraint,
          replace);
    } finally {
      writeLock.unlock();
    }
  }

};