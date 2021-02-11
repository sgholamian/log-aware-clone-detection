//,temp,sample_7030.java,2,9,temp,sample_2267.java,2,9
//,3
public class xxx {
private TimelinePutResponse processTimelineResponseErrors( TimelinePutResponse response) {
List<TimelinePutResponse.TimelinePutError> errors = response.getErrors();
if (errors.size() == 0) {


log.info("timeline entities are successfully put");
}
}

};