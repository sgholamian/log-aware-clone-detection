//,temp,TestReservationSystemWithRMHA.java,227,275,temp,TestReservationSystemWithRMHA.java,96,143
//,3
public class xxx {
  @Test
  public void testSubmitReservationFailoverAndDelete() throws Exception {
    startRMs();

    addNodeCapacityToPlan(rm1, 102400, 100);

    ClientRMService clientService = rm1.getClientRMService();

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

    // Do the failover
    explicitFailover();

    addNodeCapacityToPlan(rm2, 102400, 100);

    // check if reservation exists after failover
    Plan plan = rm2.getRMContext().getReservationSystem()
        .getPlan(ReservationSystemTestUtil.reservationQ);
    validateReservation(plan, reservationID, reservationDefinition);

    // delete the reservation
    ReservationDeleteRequest deleteRequest =
        ReservationDeleteRequest.newInstance(reservationID);
    ReservationDeleteResponse deleteResponse = null;
    clientService = rm2.getClientRMService();
    try {
      deleteResponse = clientService.deleteReservation(deleteRequest);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
    Assert.assertNotNull(deleteResponse);
    Assert.assertNull(plan.getReservationById(reservationID));
  }

};