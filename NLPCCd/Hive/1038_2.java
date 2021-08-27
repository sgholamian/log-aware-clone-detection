//,temp,sample_2866.java,2,16,temp,sample_2867.java,2,16
//,3
public class xxx {
public void dummy_method(){
Writable s2 = hrsd.serialize(s, hrsd.getObjectInspector());
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, (HCatRecord) s));
Assert.assertTrue(HCatDataCheckUtil.recordsEqual(r, (HCatRecord) s2));
LazySimpleSerDe testSD = new LazySimpleSerDe();
SerDeUtils.initializeSerDe(testSD, conf, tblProps, null);
Writable s3 = testSD.serialize(s, hrsd.getObjectInspector());
Object o3 = testSD.deserialize(s3);
Assert.assertFalse(r.getClass().equals(o3.getClass()));
HCatRecord s4 = (HCatRecord) hrsd.serialize(o3, testSD.getObjectInspector());
LazyHCatRecord s5 = new LazyHCatRecord(o3, testSD.getObjectInspector());


log.info("five");
}

};