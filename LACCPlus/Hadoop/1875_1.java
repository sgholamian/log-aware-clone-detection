//,temp,TestReservationInputValidator.java,247,268,temp,TestReservationInputValidator.java,207,224
//,3
public class xxx {
  @Test
  public void testSubmitReservationExceedsGangSize() {
    ReservationSubmissionRequest request =
        createSimpleReservationSubmissionRequest(1, 1, 1, 5, 4);
    Resource resource = Resource.newInstance(512, 1);
    when(plan.getTotalCapacity()).thenReturn(resource);
    Plan plan = null;
    try {
      plan =
          rrValidator.validateReservationSubmissionRequest(rSystem, request,
              ReservationSystemTestUtil.getNewReservationId());
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message
              .startsWith("The size of the largest gang in the reservation refinition"));
      Assert.assertTrue(message.contains("exceed the capacity available "));
      LOG.info(message);
    }
  }

};