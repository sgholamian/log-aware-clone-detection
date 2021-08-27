//,temp,sample_167.java,2,12,temp,sample_165.java,2,12
//,3
public class xxx {
public boolean addRepository(HealthCheckRepository repository) {
boolean result = repositories.add(repository);
if (result) {
if (repository instanceof CamelContextAware) {
((CamelContextAware) repository).setCamelContext(getCamelContext());


log.info("healthcheckrepository successfully registered");
}
}
}

};