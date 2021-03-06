//,temp,TestReservationInputValidator.java,410,426,temp,TestReservationInputValidator.java,359,374
//,2
public class xxx {
  @Test
  public void testUpdateReservationInvalidDeadline() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 1, 1, 0, 3);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message
          .startsWith("The specified deadline: 0 is the past"));
      LOG.info(message);
    }
  }

};