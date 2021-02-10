//,temp,TestReservationInputValidator.java,188,205,temp,TestReservationInputValidator.java,148,167
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
      Assert
          .assertTrue(message
              .equals("Missing reservation definition. Please try again by specifying a reservation definition."));
      LOG.info(message);
    }
  }

};