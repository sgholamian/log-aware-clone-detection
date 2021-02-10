//,temp,TestReservationInputValidator.java,172,189,temp,TestReservationInputValidator.java,152,170
//,3
public class xxx {
  @Test
  public void testSubmitReservationNoDefinition() {
    ReservationSubmissionRequest request =
        new ReservationSubmissionRequestPBImpl();
    request.setQueue(PLAN_NAME);
    Plan plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertEquals("Missing reservation definition. Please try again by "
          + "specifying a reservation definition.", message);
      LOG.info(message);
    }
  }

};