//,temp,TestReservationInputValidator.java,207,224,temp,TestReservationInputValidator.java,188,205
//,2
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