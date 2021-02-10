//,temp,TestReservationInputValidator.java,288,305,temp,TestReservationInputValidator.java,191,208
//,3
public class xxx {
  @Test
  public void testSubmitReservationNegativeRecurrenceExpression() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 5, 3, "-1234");
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
          .startsWith("Negative Period : "));
      LOG.info(message);
    }
  }

};