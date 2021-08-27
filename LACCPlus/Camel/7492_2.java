//,temp,BindyFixedLengthFactory.java,425,553,temp,BindyCsvFactory.java,457,563
//,3
public class xxx {
    private void generateCsvPositionMap(Class<?> clazz, Object obj, Map<Integer, List<String>> results) throws Exception {

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

                    if (datafield.trim()) {
                        result = result.trim();
                    }

                    if (datafield.clip() && result.length() > datafield.length()) {
                        result = result.substring(0, datafield.length());
                    }

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Value to be formatted: {}, position: {}, and its formatted value: {}", value,
                                datafield.pos(), result);
                    }

                } else {
                    result = "";
                }

                Integer key;

                if (isMessageOrdered() && obj != null) {

                    // Generate a key using the number of the section
                    // and the position of the field
                    Integer key1 = sections.get(obj.getClass().getName());
                    Integer key2 = datafield.position();
                    Integer keyGenerated = generateKey(key1, key2);

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Key generated: {}, for section: {}", String.valueOf(keyGenerated), key1);
                    }

                    key = keyGenerated;

                } else {
                    key = datafield.pos();
                }

                if (!results.containsKey(key)) {
                    List<String> list = new LinkedList<>();
                    list.add(result);
                    results.put(key, list);
                } else {
                    List<String> list = results.get(key);
                    list.add(result);
                }

            }

            OneToMany oneToMany = field.getAnnotation(OneToMany.class);
            if (oneToMany != null) {

                // Set global variable
                // Will be used during generation of CSV
                isOneToMany = true;

                List<?> list = (List<?>) field.get(obj);
                if (list != null) {

                    Iterator<?> it = list.iterator();
                    while (it.hasNext()) {
                        Object target = it.next();
                        generateCsvPositionMap(target.getClass(), target, results);
                    }

                } else {

                    // Call this function to add empty value
                    // in the table
                    generateCsvPositionMap(field.getClass(), null, results);
                }

            }
        }

    }

};