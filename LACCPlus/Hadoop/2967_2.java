//,temp,TestReservationInputValidator.java,487,502,temp,TestReservationInputValidator.java,210,227
//,3
public class xxx {
  @Test
  public void testSubmitReservationEmptyRR() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 0, 1, 5, 3);
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