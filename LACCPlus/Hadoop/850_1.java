//,temp,TestReservationInputValidator.java,393,408,temp,TestReservationInputValidator.java,107,125
//,3
public class xxx {
  @Test
  public void testUpdateReservationEmptyRR() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 0, 1, 5, 3);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert.assertTrue(message
          .startsWith("No resources have been specified to reserve"));
      LOG.info(message);
    }
  }

};