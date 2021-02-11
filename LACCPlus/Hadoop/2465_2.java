//,temp,TestReservationSystemWithRMHA.java,365,432,temp,TestReservationSystemWithRMHA.java,313,363
//,3
public class xxx {
  @Test
  public void testSubmitReservationFailoverAndUpdate() throws Exception {
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

    // update the reservation
    long newDeadline = reservationDefinition.getDeadline() + 100;
    reservationDefinition.setDeadline(newDeadline);
    ReservationUpdateRequest updateRequest = ReservationUpdateRequest
        .newInstance(reservationDefinition, reservationID);
    ReservationUpdateResponse updateResponse = null;
    clientService = rm2.getClientRMService();
    try {
      updateResponse = clientService.updateReservation(updateRequest);
    } catch (Exception e) {
      Assert.fail(e.getMessage());
    }
    Assert.assertNotNull(updateResponse);
    validateReservation(plan, reservationID, reservationDefinition);
  }

};