//,temp,sample_2865.java,2,16,temp,sample_2868.java,2,16
//,3
public class xxx {
public void dummy_method(){
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, (HCatRecord) s));
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, (HCatRecord) s2));
LazySimpleSerDe testSD = new LazySimpleSerDe();
SerDeUtils.initializeSerDe(testSD, conf, tblProps, null);
Writable s3 = testSD.serialize(s, hrsd.getObjectInspector());
Object o3 = testSD.deserialize(s3);
Assert.assertFalse(r.getClass().equals(o3.getClass()));
HCatRecord s4 = (HCatRecord) hrsd.serialize(o3, testSD.getObjectInspector());
LazyHCatRecord s5 = new LazyHCatRecord(o3, testSD.getObjectInspector());
LazyHCatRecord s6 = new LazyHCatRecord(s4, hrsd.getObjectInspector());


log.info("six");
}

};