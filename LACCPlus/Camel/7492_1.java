//,temp,BindyFixedLengthFactory.java,425,553,temp,BindyCsvFactory.java,457,563
//,3
public class xxx {
    private void generateFixedLengthPositionMap(Class<?> clazz, Object obj, Map<Integer, List<String>> results)
            throws Exception {

        String result = "";

        for (Field field : clazz.getDeclaredFields()) {

            field.setAccessible(true);

            DataField datafield = field.getAnnotation(DataField.class);

            if (datafield != null) {

                if (obj != null) {

                    // Create format
                    FormattingOptions formattingOptions = ConverterUtils.convert(datafield,
                            field.getType(),
                            field.getAnnotation(BindyConverter.class),
                            getLocale());
                    Format<?> format = formatFactory.getFormat(formattingOptions);

                    // Get field value
                    Object value = field.get(obj);

                    // If the field value is empty, populate it with the default value
                    if (org.apache.camel.util.ObjectHelper.isNotEmpty(datafield.defaultValue())
                            && org.apache.camel.util.ObjectHelper.isEmpty(value)) {
                        value = datafield.defaultValue();
                    }

                    result = formatString(format, value);

                    // trim if enabled
                    if (datafield.trim()) {
                        result = result.trim();
                    }

                    int fieldLength = datafield.length();

                    if (fieldLength == 0 && (datafield.lengthPos() > 0)) {
                        List<String> resultVals = results.get(datafield.lengthPos());
                        fieldLength = Integer.valueOf(resultVals.get(0));
                    }

                    if (fieldLength <= 0 && datafield.delimiter().equals("") && datafield.lengthPos() == 0) {
                        throw new IllegalArgumentException(
                                "Either a delimiter value or length for the field: "
                                                           + field.getName() + " is mandatory.");
                    }

                    if (!datafield.delimiter().equals("")) {
                        result = result + datafield.delimiter();
                    } else {
                        // Get length of the field, alignment (LEFT or RIGHT), pad
                        String align = datafield.align();
                        char padCharField = datafield.paddingChar();
                        char padChar;

                        StringBuilder temp = new StringBuilder();

                        // Check if we must pad
                        if (result.length() < fieldLength) {

                            // No padding defined for the field
                            if (padCharField == 0) {
                                // We use the padding defined for the Record
                                padChar = paddingChar;
                            } else {
                                padChar = padCharField;
                            }

                            if (align.contains("R")) {
                                temp.append(generatePaddingChars(padChar, fieldLength, result.length()));
                                temp.append(result);
                            } else if (align.contains("L")) {
                                temp.append(result);
                                temp.append(generatePaddingChars(padChar, fieldLength, result.length()));
                            } else if (align.contains("B")) {
                                temp.append(generatePaddingChars(padChar, fieldLength, result.length()));
                                temp.append(result);
                            } else {
                                throw new IllegalArgumentException(
                                        "Alignment for the field: " + field.getName()
                                                                   + " must be equal to R for RIGHT or L for LEFT or B for trimming both ends");
                            }

                            result = temp.toString();
                        } else if (result.length() > fieldLength) {
                            // we are bigger than allowed

                            // is clipped enabled? if so clip the field
                            if (datafield.clip()) {
                                result = result.substring(0, fieldLength);
                            } else {
                                throw new IllegalArgumentException(
                                        "Length for the " + field.getName()
                                                                   + " must not be larger than allowed, was: " + result.length()
                                                                   + ", allowed: " + fieldLength);
                            }
                        }
                    }

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Value to be formatted: {}, position: {}, and its formatted value: {}", value,
                                datafield.pos(), result);
                    }

                } else {
                    result = "";
                }

                Integer key;
                key = datafield.pos();

                if (!results.containsKey(key)) {
                    List<String> list = new LinkedList<>();
                    list.add(result);
                    results.put(key, list);
                } else {
                    List<String> list = results.get(key);
                    list.add(result);
                }

            }

        }

    }

};