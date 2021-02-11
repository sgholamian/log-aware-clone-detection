//,temp,SkylineStoreValidator.java,67,76,temp,SkylineStoreValidator.java,50,59
//,2
public class xxx {
  public final void validate(final RecurrenceId recurrenceId)
      throws SkylineStoreException {
    if (recurrenceId == null) {
      StringBuilder sb = new StringBuilder();
      sb.append("Recurrence id is null, please try again by specifying"
          + " a valid Recurrence id.");
      LOGGER.error(sb.toString());
      throw new NullRecurrenceIdException(sb.toString());
    }
  }

};