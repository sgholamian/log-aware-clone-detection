//,temp,CordaConsumer.java,228,233,temp,CordaConsumer.java,207,212
//,3
public class xxx {
    private void processContractStateUpdate(Vault.Update<ContractState> x) {
        LOG.debug("processContractStateUpdate {}", x);
        Exchange exchange = createExchange(true);
        exchange.getIn().setBody(x);
        processEvent(exchange);
    }

};