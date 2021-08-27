//,temp,sample_783.java,2,16,temp,sample_782.java,2,15
//,3
public class xxx {
public void testRW() throws Exception {
Configuration conf = new Configuration();
for (Pair<Properties, HCatRecord> e : getData()) {
Properties tblProps = e.first;
HCatRecord r = e.second;
HCatRecordSerDe hrsd = new HCatRecordSerDe();
SerDeUtils.initializeSerDe(hrsd, conf, tblProps, null);
JsonSerDe jsde = new JsonSerDe();
SerDeUtils.initializeSerDe(jsde, conf, tblProps, null);


log.info("orig");
}
}

};