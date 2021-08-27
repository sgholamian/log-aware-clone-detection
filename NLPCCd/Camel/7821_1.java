//,temp,sample_4841.java,2,16,temp,sample_4842.java,2,16
//,3
public class xxx {
public void dummy_method(){
snmp = new Snmp(transport);
PDU trap = exchange.getIn().getBody(PDU.class);
trap.setErrorIndex(0);
trap.setErrorStatus(0);
trap.setMaxRepetitions(0);
if (this.endpoint.getSnmpVersion() == SnmpConstants.version1) {
trap.setType(PDU.V1TRAP);
} else {
trap.setType(PDU.TRAP);
}


log.info("snmptrap sending");
}

};