//,temp,TestReservationInputValidator.java,359,374,temp,TestReservationInputValidator.java,169,186
//,3
public class xxx {
  @Test
  public void testSubmitReservationInvalidDeadline() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 0, 3);
    Plan plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message
          .startsWith("The specified deadline: 0 is the past"));
      LOG.info(message);
    }
  }

};