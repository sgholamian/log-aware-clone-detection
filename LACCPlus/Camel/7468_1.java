//,temp,CBORDataFormat.java,311,408,temp,JacksonDataFormat.java,500,630
//,3
public class xxx {
    @Override
    protected void doInit() throws Exception {
        super.doInit();

        if (unmarshalTypeName != null && (unmarshalType == null)) {
            unmarshalType = camelContext.getClassResolver().resolveClass(unmarshalTypeName);
        }
        if (collectionTypeName != null && collectionType == null) {
            Class<?> clazz = camelContext.getClassResolver().resolveClass(collectionTypeName);
            collectionType = CastUtils.cast(clazz);
        }

        if (objectMapper == null) {
            // lookup if there is a single default mapper we can use
            if (useDefaultObjectMapper && camelContext != null) {
                Set<ObjectMapper> set = camelContext.getRegistry().findByType(ObjectMapper.class);
                set = set.stream().filter(om -> om.getFactory() instanceof CBORFactory).collect(Collectors.toSet());
                if (set.size() == 1) {
                    objectMapper = set.iterator().next();
                    LOG.info(
                            "Found a single ObjectMapper with a CBORFactory in the registry, so promoting it as the default ObjectMapper: {}",
                            objectMapper);
                } else {
                    LOG.debug(
                            "Found {} ObjectMapper with a CBORFactory in the registry, so cannot promote any as the default ObjectMapper.",
                            set.size());
                }
            }
            // use a fallback object mapper in last resort
            if (objectMapper == null) {
                CBORFactory factory = new CBORFactory();
                objectMapper = new ObjectMapper(factory);
                LOG.debug("Creating new ObjectMapper to use: {}", objectMapper);
            }
        }

        if (useList) {
            setCollectionType(ArrayList.class);
        }

        if (prettyPrint) {
            objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
        }

        if (enableFeatures != null) {
            Iterator<?> it = ObjectHelper.createIterator(enableFeatures);
            while (it.hasNext()) {
                String enable = it.next().toString();
                // it can be different kind
                SerializationFeature sf = getCamelContext().getTypeConverter().tryConvertTo(SerializationFeature.class, enable);
                if (sf != null) {
                    objectMapper.enable(sf);
                    continue;
                }
                DeserializationFeature df
                        = getCamelContext().getTypeConverter().tryConvertTo(DeserializationFeature.class, enable);
                if (df != null) {
                    objectMapper.enable(df);
                    continue;
                }
                MapperFeature mf = getCamelContext().getTypeConverter().tryConvertTo(MapperFeature.class, enable);
                if (mf != null) {
                    objectMapper.enable(mf);
                    continue;
                }
                throw new IllegalArgumentException(
                        "Enable feature: " + enable
                                                   + " cannot be converted to an accepted enum of types [SerializationFeature,DeserializationFeature,MapperFeature]");
            }
        }
        if (disableFeatures != null) {
            Iterator<?> it = ObjectHelper.createIterator(disableFeatures);
            while (it.hasNext()) {
                String disable = it.next().toString();
                // it can be different kind
                SerializationFeature sf
                        = getCamelContext().getTypeConverter().tryConvertTo(SerializationFeature.class, disable);
                if (sf != null) {
                    objectMapper.disable(sf);
                    continue;
                }
                DeserializationFeature df
                        = getCamelContext().getTypeConverter().tryConvertTo(DeserializationFeature.class, disable);
                if (df != null) {
                    objectMapper.disable(df);
                    continue;
                }
                MapperFeature mf = getCamelContext().getTypeConverter().tryConvertTo(MapperFeature.class, disable);
                if (mf != null) {
                    objectMapper.disable(mf);
                    continue;
                }
                throw new IllegalArgumentException(
                        "Disable feature: " + disable
                                                   + " cannot be converted to an accepted enum of types [SerializationFeature,DeserializationFeature,MapperFeature]");
            }
        }
    }

};