//,temp,TestReservationInputValidator.java,487,502,temp,TestReservationInputValidator.java,307,325
//,3
public class xxx {
  @Test
  public void testSubmitReservationMaxPeriodIndivisibleByRecurrenceExp() {
    long indivisibleRecurrence =
        YarnConfiguration.DEFAULT_RM_RESERVATION_SYSTEM_MAX_PERIODICITY / 2 + 1;
    String recurrenceExp = Long.toString(indivisibleRecurrence);
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 5, 3, recurrenceExp);
    plan = null;
    try {
      plan = rrValidator.validateReservationSubmissionRequest(rSystem, request,
          ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message.startsWith("The maximum periodicity:"));
      LOG.info(message);
    }
  }

};