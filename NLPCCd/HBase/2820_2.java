//,temp,sample_1260.java,2,9,temp,sample_1259.java,2,8
//,3
public class xxx {
protected void addToMovedRegions(String encodedName, ServerName destination, long closeSeqNum) {
if (ServerName.isSameAddress(destination, this.getServerName())) {


log.info("not adding moved region record to self");
}
}

};