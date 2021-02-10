//,temp,TestReservationInputValidator.java,744,760,temp,TestReservationInputValidator.java,487,502
//,3
public class xxx {
  @Test
  public void testListReservationsEmptyQueue() {
    ReservationListRequest request = new ReservationListRequestPBImpl();
    request.setQueue("");
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationListRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message.equals(
          "The queue is not specified. Please try again with a valid " +
                                      "reservable queue."));
      LOG.info(message);
    }
  }

};