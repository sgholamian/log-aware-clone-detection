//,temp,sample_3768.java,2,17,temp,sample_3767.java,2,17
//,3
public class xxx {
public void dummy_method(){
int unionByteEnd = start + length;
byte[] byteArr = this.bytes.getData();
final int tagEnd = start + 1;
tag = byteArr[start];
field = LazyBinaryFactory.createLazyBinaryObject(uoi.getObjectInspectors().get(tag));
LazyBinaryUtils.checkObjectByteInfo(uoi.getObjectInspectors().get(tag), byteArr, tagEnd, recordInfo, vInt);
fieldStart = tagEnd + recordInfo.elementOffset;
fieldLength = recordInfo.elementSize;
if (!extraFieldWarned &&  (fieldStart + fieldLength) < unionByteEnd) {
extraFieldWarned = true;


log.info("extra bytes detected at the end of the row ignoring similar problems");
}
}

};