//,temp,TestReservationInputValidator.java,487,502,temp,TestReservationInputValidator.java,416,433
//,3
public class xxx {
  @Test
  public void testUpdateReservationInvalidPlan() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 1, 1, 5, 4);
    when(rSystem.getPlan(PLAN_NAME)).thenReturn(null);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
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