//,temp,TestReservationInputValidator.java,470,485,temp,TestReservationInputValidator.java,229,248
//,3
public class xxx {
  @Test
  public void testUpdateReservationInvalidRR() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(0, 0, 1, 5, 3);
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