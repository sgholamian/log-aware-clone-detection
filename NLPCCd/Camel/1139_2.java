//,temp,sample_6724.java,2,15,temp,sample_6722.java,2,11
//,3
public class xxx {
public void process(Exchange exchange) throws Exception {
ExecCommand execCommand = getBinding().readInput(exchange, endpoint);
ExecCommandExecutor executor = endpoint.getCommandExecutor();
if (executor == null) {
executor = new DefaultExecCommandExecutor();
}


log.info("executing");
}

};