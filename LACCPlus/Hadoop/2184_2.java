//,temp,TestReservationInputValidator.java,152,170,temp,TestReservationInputValidator.java,112,129
//,3
public class xxx {
  @Test
  public void testSubmitReservationDoesNotExist() {
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
      Assert.assertEquals("The queue is not specified. Please try again with a "
          + "valid reservable queue.", message);
      LOG.info(message);
    }
  }

};