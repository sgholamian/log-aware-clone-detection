//,temp,InMemoryStore.java,158,189,temp,InMemoryStore.java,76,121
//,3
public class xxx {
  @Override public final void addHistory(final RecurrenceId recurrenceId,
      final List<ResourceSkyline> resourceSkylines)
      throws SkylineStoreException {
    inputValidator.validate(recurrenceId, resourceSkylines);
    writeLock.lock();
    try {
      // remove the null elements in the resourceSkylines
      final List<ResourceSkyline> filteredInput =
          eliminateNull(resourceSkylines);
      if (filteredInput.size() > 0) {
        if (skylineStore.containsKey(recurrenceId)) {
          // if filteredInput has duplicate jobIds with existing skylines in the
          // store,
          // throw out an exception
          final List<ResourceSkyline> jobHistory =
              skylineStore.get(recurrenceId);
          final List<String> oldJobIds = new ArrayList<>();
          for (final ResourceSkyline resourceSkyline : jobHistory) {
            oldJobIds.add(resourceSkyline.getJobId());
          }
          if (!oldJobIds.isEmpty()) {
            for (ResourceSkyline elem : filteredInput) {
              if (oldJobIds.contains(elem.getJobId())) {
                StringBuilder errMsg = new StringBuilder();
                errMsg.append(
                    "Trying to addHistory duplicate resource skylines for "
                        + recurrenceId
                        + ". Use updateHistory function instead.");
                LOGGER.error(errMsg.toString());
                throw new DuplicateRecurrenceIdException(errMsg.toString());
              }
            }
          }
          skylineStore.get(recurrenceId).addAll(filteredInput);
          LOGGER.info("Successfully addHistory new resource skylines for {}.",
              recurrenceId);
        } else {
          skylineStore.put(recurrenceId, filteredInput);
          LOGGER.info("Successfully addHistory new resource skylines for {}.",
              recurrenceId);
        }
      }
    } finally {
      writeLock.unlock();
    }
  }

};