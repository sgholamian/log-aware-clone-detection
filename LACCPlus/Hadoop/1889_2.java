//,temp,TestReservationInputValidator.java,505,524,temp,TestReservationInputValidator.java,341,357
//,3
public class xxx {
  @Test
  public void testUpdateReservationNoDefinition() {
    ReservationUpdateRequest request = new ReservationUpdateRequestPBImpl();
    request.setReservationId(ReservationSystemTestUtil.getNewReservationId());
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message
              .startsWith("Missing reservation definition. Please try again by specifying a reservation definition."));
      LOG.info(message);
    }
  }

};