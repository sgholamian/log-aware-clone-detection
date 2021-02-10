//,temp,TestReservationInputValidator.java,575,608,temp,TestReservationInputValidator.java,327,362
//,3
public class xxx {
  @Test
  public void testSubmitReservationInvalidRecurrenceExpression() {
    // first check recurrence expression
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 5, 3, "123abc");
    plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message
          .startsWith("Invalid period "));
      LOG.info(message);
    }

    // now check duration
    request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 50, 3, "10");
    plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message
          .startsWith("Duration of the requested reservation:"));
      LOG.info(message);
    }
  }

};