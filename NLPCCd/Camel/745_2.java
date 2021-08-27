//,temp,sample_5784.java,2,13,temp,sample_3538.java,2,14
//,3
public class xxx {
public static String generateInvalidPayloadExceptionMessage(final byte[] hl7Bytes, final int length) {
if (hl7Bytes == null) {
return "HL7 payload is null";
}
if (hl7Bytes.length <= 0) {
return "HL7 payload is empty";
}
if (length > hl7Bytes.length) {


log.info("the length specified for the payload array is greater than the actual length of the array only validating bytes");
}
}

};