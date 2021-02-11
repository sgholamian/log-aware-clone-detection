//,temp,TestReservationInputValidator.java,575,608,temp,TestReservationInputValidator.java,522,541
//,3
public class xxx {
  @Test
  public void testUpdateReservationInvalidRecurrenceExpression() {
    // first check recurrence expression
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 1, 1, 5, 3, "123abc");
    plan = null;
    try {
      plan =
          rrValidator.validateReservationUpdateRequest(rSystem, request);
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
        createSimpleReservationUpdateRequest(1, 1, 1, 50, 3, "10");
    plan = null;
    try {
      plan =
          rrValidator.validateReservationUpdateRequest(rSystem, request);
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