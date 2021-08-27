//,temp,BindyFixedLengthFactory.java,100,154,temp,BindyCsvFactory.java,103,165
//,3
public class xxx {
    @Override
    public void initAnnotatedFields() {

        for (Class<?> cl : models) {

            List<Field> linkFields = new ArrayList<>();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Class retrieved: {}", cl.getName());
            }

            for (Field field : cl.getDeclaredFields()) {
                DataField dataField = field.getAnnotation(DataField.class);
                if (dataField != null) {

                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Position defined in the class: {}, position: {}, Field: {}", cl.getName(), dataField.pos(),
                                dataField);
                    }

                    if (dataField.required()) {
                        ++numberMandatoryFields;
                    } else {
                        ++numberOptionalFields;
                    }

                    dataFields.put(dataField.pos(), dataField);
                    annotatedFields.put(dataField.pos(), field);
                }

                Link linkField = field.getAnnotation(Link.class);

                if (linkField != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Class linked: {}, Field: {}", cl.getName(), field);
                    }
                    linkFields.add(field);
                }

            }

            if (!linkFields.isEmpty()) {
                annotatedLinkFields.put(cl.getName(), linkFields);
            }

            totalFields = numberMandatoryFields + numberOptionalFields;

            if (LOG.isDebugEnabled()) {
                LOG.debug("Number of optional fields: {}", numberOptionalFields);
                LOG.debug("Number of mandatory fields: {}", numberMandatoryFields);
                LOG.debug("Total: {}", totalFields);
            }

        }
    }

};