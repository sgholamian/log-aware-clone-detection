//,temp,sample_3293.java,2,16,temp,sample_3301.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertNotNull(resID2);
ReservationId resID3 = getNewReservation(clientService) .getReservationId();
request.setReservationId(resID3);
try {
response = clientService.submitReservation(request);
} catch (Exception e) {
Assert.fail(e.getMessage());
}
Assert.assertNotNull(response);
Assert.assertNotNull(resID3);


log.info("submit reservation response");
}

};