//,temp,sample_3295.java,2,16,temp,sample_3300.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertNotNull(resID1);
ReservationId resID2 = getNewReservation(clientService) .getReservationId();
request.setReservationId(resID2);
try {
response = clientService.submitReservation(request);
} catch (Exception e) {
Assert.fail(e.getMessage());
}
Assert.assertNotNull(response);
Assert.assertNotNull(resID2);


log.info("submit reservation response");
}

};