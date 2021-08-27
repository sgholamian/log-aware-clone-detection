//,temp,sample_3768.java,2,17,temp,sample_3767.java,2,17
//,3
public class xxx {
public void dummy_method(){
tag = byteArr[start];
field = LazyBinaryFactory.createLazyBinaryObject(uoi.getObjectInspectors().get(tag));
LazyBinaryUtils.checkObjectByteInfo(uoi.getObjectInspectors().get(tag), byteArr, tagEnd, recordInfo, vInt);
fieldStart = tagEnd + recordInfo.elementOffset;
fieldLength = recordInfo.elementSize;
if (!extraFieldWarned &&  (fieldStart + fieldLength) < unionByteEnd) {
extraFieldWarned = true;
}
if (!missingFieldWarned && (fieldStart + fieldLength) > unionByteEnd) {
missingFieldWarned = true;


log.info("missing fields expected fields but only got ignoring similar problems");
}
}

};