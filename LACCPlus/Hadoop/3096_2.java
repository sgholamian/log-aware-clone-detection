//,temp,TestReservationSystemWithRMHA.java,313,363,temp,TestReservationSystemWithRMHA.java,96,143
//,3
public class xxx {
  @Test
  public void testUpdateReservationAndCheckAfterFailover() throws Exception {
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

    // Change any field

    long newDeadline = reservationDefinition.getDeadline() + 100;
    reservationDefinition.setDeadline(newDeadline);
    ReservationUpdateRequest updateRequest = ReservationUpdateRequest
        .newInstance(reservationDefinition, reservationID);
    rm1.updateReservationState(updateRequest);

    // Do the failover
    explicitFailover();

    rm2.registerNode("127.0.0.1:1", 102400, 100);

    RMState state = rm2.getRMContext().getStateStore().loadState();
    Map<ReservationId, ReservationAllocationStateProto> reservationStateMap =
        state.getReservationState().get(ReservationSystemTestUtil.reservationQ);
    Assert.assertNotNull(reservationStateMap);
    ReservationAllocationStateProto reservationState =
        reservationStateMap.get(reservationID);
    Assert.assertEquals(newDeadline,
        reservationState.getReservationDefinition().getDeadline());
  }

};