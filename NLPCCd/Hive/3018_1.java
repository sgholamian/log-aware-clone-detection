//,temp,sample_2864.java,2,17,temp,sample_2863.java,2,14
//,3
public class xxx {
public void dummy_method(){
Configuration conf = new Configuration();
for (Entry<Properties, HCatRecord> e : getData().entrySet()) {
Properties tblProps = e.getKey();
HCatRecord r = e.getValue();
HCatRecordSerDe hrsd = new HCatRecordSerDe();
SerDeUtils.initializeSerDe(hrsd, conf, tblProps, null);
Writable s = hrsd.serialize(r, hrsd.getObjectInspector());
HCatRecord r2 = (HCatRecord) hrsd.deserialize(s);
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, r2));
Writable s2 = hrsd.serialize(s, hrsd.getObjectInspector());


log.info("two");
}
}

};