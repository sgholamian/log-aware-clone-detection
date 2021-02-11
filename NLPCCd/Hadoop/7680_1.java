//,temp,sample_4201.java,2,12,temp,sample_4301.java,2,13
//,3
public class xxx {
private void parseAclsWithPrefix(final Configuration conf, final String prefix, final KeyOpType keyOp, Map<KeyOpType, AccessControlList> results) {
String confKey = prefix + keyOp;
String aclStr = conf.get(confKey);
if (aclStr != null) {
if (keyOp == KeyOpType.ALL) {


log.info("invalid key op for ignoring");
}
}
}

};