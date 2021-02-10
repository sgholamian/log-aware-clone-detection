//,temp,TestReservationInputValidator.java,250,271,temp,TestReservationInputValidator.java,131,150
//,3
public class xxx {
  @Test
  public void testSubmitReservationInvalidPlan() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 5, 3);
    when(rSystem.getPlan(PLAN_NAME)).thenReturn(null);
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
              .endsWith(" is not managed by reservation system. Please try again with a valid reservable queue."));
      LOG.info(message);
    }
  }

};