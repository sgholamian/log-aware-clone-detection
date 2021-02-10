//,temp,TestReservationInputValidator.java,557,573,temp,TestReservationInputValidator.java,191,208
//,3
public class xxx {
  @Test
  public void testUpdateReservationNegativeRecurrenceExpression() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 1, 1, 5, 3, "-1234");
    plan = null;
    try {
      plan =
          rrValidator.validateReservationUpdateRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message
          .startsWith("Negative Period : "));
      LOG.info(message);
    }
  }

};