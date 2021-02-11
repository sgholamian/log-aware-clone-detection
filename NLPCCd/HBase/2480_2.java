//,temp,sample_577.java,2,14,temp,sample_1252.java,2,13
//,3
public class xxx {
protected boolean closeRegion(String encodedName, final boolean abort, final ServerName sn) throws NotServingRegionException {
HRegion actualRegion = this.getRegion(encodedName);
if ((actualRegion != null) && (actualRegion.getCoprocessorHost() != null)) {
try {
actualRegion.getCoprocessorHost().preClose(false);
} catch (IOException exp) {


log.info("unable to close region the coprocessor launched an error");
}
}
}

};