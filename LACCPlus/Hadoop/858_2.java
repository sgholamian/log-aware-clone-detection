//,temp,TestReservationInputValidator.java,483,503,temp,TestReservationInputValidator.java,300,320
//,3
public class xxx {
  @Test
  public void testUpdateReservationDoesnotExist() {
    ReservationUpdateRequest request =
        createSimpleReservationUpdateRequest(1, 1, 1, 5, 4);
    ReservationId rId = request.getReservationId();
    when(rSystem.getQueueForReservation(rId)).thenReturn(null);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationUpdateRequest(rSystem, request);
      Assert.fail();
    } catch (YarnException e) {
      Assert.assertNull(plan);
      String message = e.getMessage();
      Assert
          .assertTrue(message.equals(MessageFormat
              .format(
                  "The specified reservation with ID: {0} is unknown. Please try again with a valid reservation.",
                  rId)));
      LOG.info(message);
    }
  }

};