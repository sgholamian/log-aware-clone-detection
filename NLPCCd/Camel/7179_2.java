//,temp,sample_167.java,2,12,temp,sample_165.java,2,12
//,3
public class xxx {
public boolean register(HealthCheck check) {
boolean result = checks.add(check);
if (result) {
if (check instanceof CamelContextAware) {
((CamelContextAware) check).setCamelContext(camelContext);
}


log.info("healthcheck with id successfully registered");
}
}

};