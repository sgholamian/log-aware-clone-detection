//,temp,sample_8955.java,2,12,temp,sample_5851.java,2,12
//,2
public class xxx {
public void create() {
try {
HealthCheckPolicy result = _lbService.createLBHealthCheckPolicy(this);
this.setEntityId(result.getId());
this.setEntityUuid(result.getUuid());
} catch (InvalidParameterValueException e) {


log.info("exception");
}
}

};