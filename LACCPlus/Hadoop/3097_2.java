//,temp,TestReservationSystemWithRMHA.java,313,363,temp,TestReservationSystemWithRMHA.java,277,311
//,3
public class xxx {
  @Test
  public void testFailoverAndSubmitReservation() throws Exception {
    startRMs();

    addNodeCapacityToPlan(rm1, 102400, 100);

    // Do the failover
    explicitFailover();

    addNodeCapacityToPlan(rm2, 102400, 100);
    ClientRMService clientService = rm2.getClientRMService();

    ReservationId reservationID = getNewReservation(clientService)
        .getReservationId();

    // create a reservation
    ReservationSubmissionRequest request = createReservationSubmissionRequest(
        reservationID);
    ReservationSubmissionResponse response = null;
    try {
      response = clientService.submitReservation(request);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
    Assert.assertNotNull(response);
    Assert.assertNotNull(reservationID);
    LOG.info("Submit reservation response: " + reservationID);
    ReservationDefinition reservationDefinition =
        request.getReservationDefinition();

    // check if reservation is submitted successfully
    Plan plan = rm2.getRMContext().getReservationSystem()
        .getPlan(ReservationSystemTestUtil.reservationQ);
    validateReservation(plan, reservationID, reservationDefinition);
  }

};