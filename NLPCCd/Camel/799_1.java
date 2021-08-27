//,temp,sample_2535.java,2,10,temp,sample_7132.java,2,11
//,3
public class xxx {
private String getSolverId(Exchange exchange) throws Exception {
String solverId = exchange.getIn().getHeader(OptaPlannerConstants.SOLVER_ID, String.class);
if (solverId == null) {
solverId = configuration.getSolverId();
}


log.info("solverid");
}

};