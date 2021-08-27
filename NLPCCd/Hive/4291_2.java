//,temp,sample_784.java,2,16,temp,sample_785.java,2,16
//,3
public class xxx {
public void dummy_method(){
SerDeUtils.initializeSerDe(hrsd, conf, tblProps, null);
JsonSerDe jsde = new JsonSerDe();
SerDeUtils.initializeSerDe(jsde, conf, tblProps, null);
Writable s = hrsd.serialize(r, hrsd.getObjectInspector());
Object o1 = hrsd.deserialize(s);
StringBuilder msg = new StringBuilder();
boolean isEqual = HCatDataCheckUtil.recordsEqual(r, (HCatRecord) o1);
assertTrue(msg.toString(), isEqual);
Writable s2 = jsde.serialize(o1, hrsd.getObjectInspector());
Object o2 = jsde.deserialize(s2);


log.info("deserialized two");
}

};