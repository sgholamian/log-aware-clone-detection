//,temp,TestReservationInputValidator.java,229,248,temp,TestReservationInputValidator.java,152,170
//,3
public class xxx {
  @Test
  public void testSubmitReservationInvalidDuration() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 3, 4);
    Plan plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message.startsWith("The time difference"));
      Assert
          .assertTrue(message
              .contains("must  be greater or equal to the minimum resource duration"));
      LOG.info(message);
    }
  }

};