//,temp,sample_3293.java,2,16,temp,sample_3301.java,2,16
//,3
public class xxx {
public void dummy_method(){
ReservationId reservationID = getNewReservation(clientService) .getReservationId();
ReservationSubmissionRequest request = createReservationSubmissionRequest( reservationID);
ReservationSubmissionResponse response = null;
try {
response = clientService.submitReservation(request);
} catch (Exception e) {
Assert.fail(e.getMessage());
}
Assert.assertNotNull(response);
Assert.assertNotNull(reservationID);


log.info("submit reservation response");
}

};