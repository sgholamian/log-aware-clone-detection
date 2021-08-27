//,temp,sample_5382.java,2,16,temp,sample_4266.java,2,17
//,3
public class xxx {
public Object deserialize(Writable blob) throws SerDeException {
Text rowText = (Text) blob;
Matcher m = inputPattern.matcher(rowText.toString());
if (m.groupCount() != numColumns) {
throw new SerDeException("Number of matching groups doesn't match the number of columns");
}
if (!m.matches()) {
unmatchedRowsCount++;
if (!alreadyLoggedNoMatch) {


log.info("unmatched rows are found");
}
}
}

};