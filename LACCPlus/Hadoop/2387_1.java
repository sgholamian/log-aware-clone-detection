//,temp,InMemoryStore.java,158,189,temp,InMemoryStore.java,76,121
//,3
public class xxx {
  @Override public final void updateHistory(final RecurrenceId recurrenceId,
      final List<ResourceSkyline> resourceSkylines)
      throws SkylineStoreException {
    inputValidator.validate(recurrenceId, resourceSkylines);
    writeLock.lock();
    try {
      if (skylineStore.containsKey(recurrenceId)) {
        // remove the null elements in the resourceSkylines
        List<ResourceSkyline> filteredInput = eliminateNull(resourceSkylines);
        if (filteredInput.size() > 0) {
          skylineStore.put(recurrenceId, filteredInput);
          LOGGER.info("Successfully updateHistory resource skylines for {}.",
              recurrenceId);
        } else {
          StringBuilder errMsg = new StringBuilder();
          errMsg.append("Trying to updateHistory " + recurrenceId
              + " with empty resource skyline");
          LOGGER.error(errMsg.toString());
          throw new EmptyResourceSkylineException(errMsg.toString());
        }
      } else {
        StringBuilder errMsg = new StringBuilder();
        errMsg.append(
            "Trying to updateHistory non-existing resource skylines for "
                + recurrenceId);
        LOGGER.error(errMsg.toString());
        throw new RecurrenceIdNotFoundException(errMsg.toString());
      }
    } finally {
      writeLock.unlock();
    }
  }

};