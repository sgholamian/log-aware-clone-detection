//,temp,TestReservationInputValidator.java,504,520,temp,TestReservationInputValidator.java,172,189
//,3
public class xxx {
  @Test
  public void testUpdateReservationInvalidDuration() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 1, 1, 3, 4);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message
              .contains("must  be greater or equal to the minimum resource duration"));
      LOG.info(message);
    }
  }

};