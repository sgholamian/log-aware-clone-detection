//,temp,sample_963.java,2,17,temp,sample_964.java,2,17
//,3
public class xxx {
public void dummy_method(){
try {
if (showIndexes.isFormatted()) {
outStream.write(MetaDataFormatUtils.getIndexColumnsHeader().getBytes(StandardCharsets.UTF_8));
}
for (Index index : indexes) {
outStream.write(MetaDataFormatUtils.getIndexInformation(index, isOutputPadded).getBytes(StandardCharsets.UTF_8));
}
} catch (FileNotFoundException e) {
throw new HiveException(e.toString());
} catch (IOException e) {


log.info("show indexes");
}
}

};