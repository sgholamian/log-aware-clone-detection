//,temp,TestReservationSystemWithRMHA.java,227,275,temp,TestReservationSystemWithRMHA.java,61,94
//,3
public class xxx {
  @Test
  public void testSubmitReservationAndCheckAfterFailover() throws Exception {
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

    // Do the failover
    explicitFailover();

    rm2.registerNode("127.0.0.1:1", 102400, 100);

    RMState state = rm2.getRMContext().getStateStore().loadState();
    Map<ReservationId, ReservationAllocationStateProto> reservationStateMap =
        state.getReservationState().get(ReservationSystemTestUtil.reservationQ);
    Assert.assertNotNull(reservationStateMap);
    Assert.assertNotNull(reservationStateMap.get(reservationID));
  }

};