//,temp,sample_2865.java,2,16,temp,sample_2868.java,2,16
//,3
public class xxx {
public void dummy_method(){
SerDeUtils.initializeSerDe(hrsd, conf, tblProps, null);
Writable s = hrsd.serialize(r, hrsd.getObjectInspector());
HCatRecord r2 = (HCatRecord) hrsd.deserialize(s);
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, r2));
Writable s2 = hrsd.serialize(s, hrsd.getObjectInspector());
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, (HCatRecord) s));
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, (HCatRecord) s2));
LazySimpleSerDe testSD = new LazySimpleSerDe();
SerDeUtils.initializeSerDe(testSD, conf, tblProps, null);
Writable s3 = testSD.serialize(s, hrsd.getObjectInspector());


log.info("three");
}

};