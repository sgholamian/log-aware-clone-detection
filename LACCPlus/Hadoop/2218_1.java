//,temp,TestReservationInputValidator.java,762,782,temp,TestReservationInputValidator.java,131,150
//,3
public class xxx {
  @Test
  public void testListReservationsNullPlan() {
    ReservationListRequest request = new ReservationListRequestPBImpl();
    request.setQueue(ReservationSystemTestUtil.reservationQ);
    when(rSystem.getPlan(ReservationSystemTestUtil.reservationQ)).thenReturn
            (null);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationListRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message.equals(
              "The specified queue: " + ReservationSystemTestUtil.reservationQ
            + " is not managed by reservation system."
            + " Please try again with a valid reservable queue."
      ));
      LOG.info(message);
    }
  }

};