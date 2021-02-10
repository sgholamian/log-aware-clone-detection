//,temp,TestReservationInputValidator.java,505,524,temp,TestReservationInputValidator.java,483,503
//,3
public class xxx {
  @Test
  public void testDeleteReservationDoesnotExist() {
    ReservationDeleteRequest request = new ReservationDeleteRequestPBImpl();
    ReservationId rId = ReservationSystemTestUtil.getNewReservationId();
    request.setReservationId(rId);
    when(rSystem.getQueueForReservation(rId)).thenReturn(null);
    Plan plan = null;
    try {
      plan = rrValidator.validateReservationDeleteRequest(rSystem, request);
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