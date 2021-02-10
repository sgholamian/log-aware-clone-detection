//,temp,TestReservationInputValidator.java,744,760,temp,TestReservationInputValidator.java,191,208
//,3
public class xxx {
  @Test
  public void testSubmitReservationInvalidRR() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(0, 0, 1, 5, 3);
    Plan plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
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