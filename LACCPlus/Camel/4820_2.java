//,temp,BindyFixedLengthFactory.java,100,154,temp,BindyCsvFactory.java,103,165
//,3
public class xxx {
    @Override
    public void initAnnotatedFields() {

        maxpos = 0;
        for (Class<?> cl : models) {
            List<Field> linkFields = new ArrayList<>();

            if (LOG.isDebugEnabled()) {
                LOG.debug("Class retrieved: {}", cl.getName());
            }

            for (Field field : cl.getDeclaredFields()) {
                DataField dataField = field.getAnnotation(DataField.class);
                if (dataField != null) {
                    if (LOG.isDebugEnabled()) {
                        LOG.debug("Position defined in the class: {}, position: {}, Field: {}",
                                new Object[] { cl.getName(), dataField.pos(), dataField });
                    }

                    if (dataField.required()) {
                        ++numberMandatoryFields;
                    } else {
                        ++numberOptionalFields;
                    }

                    int pos = dataField.pos();
                    if (annotatedFields.containsKey(pos)) {
                        Field f = annotatedFields.get(pos);
                        LOG.warn("Potentially invalid model: existing @DataField '{}' replaced by '{}'", f.getName(),
                                field.getName());
                    }
                    dataFields.put(pos, dataField);
                    annotatedFields.put(pos, field);
                    maxpos = Math.max(maxpos, pos);
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

        if (annotatedFields.size() < maxpos) {
            LOG.info("Potentially incomplete model: some csv fields may not be mapped to @DataField members");
        }
    }

};