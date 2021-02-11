//,temp,TestReservationInputValidator.java,466,481,temp,TestReservationInputValidator.java,169,186
//,3
public class xxx {
  @Test
  public void testDeleteReservationNoID() {
    ReservationDeleteRequest request = new ReservationDeleteRequestPBImpl();
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationDeleteRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message
              .startsWith("Missing reservation id. Please try again by specifying a reservation id."));
      LOG.info(message);
    }
  }

};