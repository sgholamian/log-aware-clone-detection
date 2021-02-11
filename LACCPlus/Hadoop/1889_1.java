//,temp,TestReservationInputValidator.java,505,524,temp,TestReservationInputValidator.java,341,357
//,3
public class xxx {
  @Test
  public void testDeleteReservationInvalidPlan() {
    ReservationDeleteRequest request = new ReservationDeleteRequestPBImpl();
    ReservationId reservationID =
        ReservationSystemTestUtil.getNewReservationId();
    request.setReservationId(reservationID);
    when(rSystem.getPlan(PLAN_NAME)).thenReturn(null);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationDeleteRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message
              .endsWith(" is not associated with any valid plan. Please try again with a valid reservation."));
      LOG.info(message);
    }
  }

};