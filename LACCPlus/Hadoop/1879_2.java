//,temp,TestReservationInputValidator.java,376,391,temp,TestReservationInputValidator.java,283,298
//,3
public class xxx {
  @Test
  public void testUpdateReservationNoID() {
    ReservationUpdateRequest request = new ReservationUpdateRequestPBImpl();
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
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