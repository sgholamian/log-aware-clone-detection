//,temp,TestReservationInputValidator.java,410,426,temp,TestReservationInputValidator.java,107,125
//,3
public class xxx {
  @Test
  public void testSubmitReservationDoesnotExist() {
    ReservationSubmissionRequest request =
        new ReservationSubmissionRequestPBImpl();
    Plan plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message
              .equals("The queue to submit is not specified. Please try again with a valid reservable queue."));
      LOG.info(message);
    }
  }

};