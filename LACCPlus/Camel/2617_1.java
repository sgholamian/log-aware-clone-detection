//,temp,Sqs2Producer.java,276,334,temp,Sns2Producer.java,95,135
//,3
public class xxx {
    Map<String, MessageAttributeValue> translateAttributes(Map<String, Object> headers, Exchange exchange) {
        Map<String, MessageAttributeValue> result = new HashMap<>();
        HeaderFilterStrategy headerFilterStrategy = getEndpoint().getHeaderFilterStrategy();
        for (Entry<String, Object> entry : headers.entrySet()) {
            // only put the message header which is not filtered into the
            // message attribute
            if (!headerFilterStrategy.applyFilterToCamelHeaders(entry.getKey(), entry.getValue(), exchange)) {
                Object value = entry.getValue();
                if (value instanceof String && !((String) value).isEmpty()) {
                    MessageAttributeValue.Builder mav = MessageAttributeValue.builder();
                    mav.dataType("String");
                    mav.stringValue((String) value);
                    result.put(entry.getKey(), mav.build());
                } else if (value instanceof ByteBuffer) {
                    MessageAttributeValue.Builder mav = MessageAttributeValue.builder();
                    mav.dataType("Binary");
                    mav.binaryValue(SdkBytes.fromByteBuffer((ByteBuffer) value));
                    result.put(entry.getKey(), mav.build());
                } else if (value instanceof Boolean) {
                    MessageAttributeValue.Builder mav = MessageAttributeValue.builder();
                    mav.dataType("Number.Boolean");
                    mav.stringValue(((Boolean) value) ? "1" : "0");
                    result.put(entry.getKey(), mav.build());
                } else if (value instanceof Number) {
                    MessageAttributeValue.Builder mav = MessageAttributeValue.builder();
                    final String dataType;
                    if (value instanceof Integer) {
                        dataType = "Number.int";
                    } else if (value instanceof Byte) {
                        dataType = "Number.byte";
                    } else if (value instanceof Double) {
                        dataType = "Number.double";
                    } else if (value instanceof Float) {
                        dataType = "Number.float";
                    } else if (value instanceof Long) {
                        dataType = "Number.long";
                    } else if (value instanceof Short) {
                        dataType = "Number.short";
                    } else {
                        dataType = "Number";
                    }
                    mav.dataType(dataType);
                    mav.stringValue(value.toString());
                    result.put(entry.getKey(), mav.build());
                } else if (value instanceof Date) {
                    MessageAttributeValue.Builder mav = MessageAttributeValue.builder();
                    mav.dataType("String");
                    mav.stringValue(value.toString());
                    result.put(entry.getKey(), mav.build());
                } else {
                    // cannot translate the message header to message attribute
                    // value
                    LOG.warn("Cannot put the message header key={}, value={} into Sqs MessageAttribute", entry.getKey(),
                            entry.getValue());
                }
            }
        }
        return result;
    }

};