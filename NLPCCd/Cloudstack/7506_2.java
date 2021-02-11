//,temp,sample_9538.java,2,10,temp,sample_6688.java,2,10
//,2
public class xxx {
private boolean isImplicitPlannerUsedByOffering(long offeringId) {
boolean implicitPlannerUsed = false;
ServiceOfferingVO offering = serviceOfferingDao.findByIdIncludingRemoved(offeringId);
if (offering == null) {


log.info("couldn t retrieve the offering by the given id");
}
}

};