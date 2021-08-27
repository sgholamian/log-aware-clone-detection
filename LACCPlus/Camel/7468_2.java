//,temp,CBORDataFormat.java,311,408,temp,JacksonDataFormat.java,500,630
//,3
public class xxx {
    @Override
    protected void doStart() throws Exception {
        boolean objectMapperFoundRegistry = false;
        if (objectMapper == null) {
            // lookup if there is a single default mapper we can use
            if (useDefaultObjectMapper && camelContext != null) {
                if (isAutoDiscoverObjectMapper()) {
                    Set<ObjectMapper> set = camelContext.getRegistry().findByType(ObjectMapper.class);
                    if (set.size() == 1) {
                        objectMapper = set.iterator().next();
                        LOG.info("Found single ObjectMapper in Registry to use: {}", objectMapper);
                        objectMapperFoundRegistry = true;
                    } else if (set.size() > 1) {
                        LOG.debug(
                                "Found {} ObjectMapper in Registry cannot use as default as there are more than one instance.",
                                set.size());
                    }
                } else {
                    LOG.info("The option autoDiscoverObjectMapper is set to false, Camel won't search in the registry");
                }
            }
            if (objectMapper == null) {
                objectMapper = new ObjectMapper();
                LOG.debug("Creating new ObjectMapper to use: {}", objectMapper);
            }
        }

        if (!objectMapperFoundRegistry) {
            if (useList) {
                setCollectionType(ArrayList.class);
            }
            if (include != null) {
                JsonInclude.Include inc
                        = getCamelContext().getTypeConverter().mandatoryConvertTo(JsonInclude.Include.class, include);
                objectMapper.setSerializationInclusion(inc);
            }
            if (prettyPrint) {
                objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
            }

            if (enableFeatures != null) {
                Iterator<?> it = ObjectHelper.createIterator(enableFeatures);
                while (it.hasNext()) {
                    String enable = it.next().toString();
                    // it can be different kind
                    SerializationFeature sf
                            = getCamelContext().getTypeConverter().tryConvertTo(SerializationFeature.class, enable);
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

            if (modules != null) {
                for (Module module : modules) {
                    LOG.debug("Registering module: {}", module);
                    objectMapper.registerModules(module);
                }
            }
            if (moduleClassNames != null) {
                Iterable<?> it = ObjectHelper.createIterable(moduleClassNames);
                for (Object o : it) {
                    String name = o.toString();
                    Class<Module> clazz = camelContext.getClassResolver().resolveMandatoryClass(name, Module.class);
                    Module module = camelContext.getInjector().newInstance(clazz);
                    LOG.debug("Registering module: {} -> {}", name, module);
                    objectMapper.registerModule(module);
                }
            }
            if (moduleRefs != null) {
                Iterable<?> it = ObjectHelper.createIterable(moduleRefs);
                for (Object o : it) {
                    String name = o.toString();
                    if (name.startsWith("#")) {
                        name = name.substring(1);
                    }
                    Module module = CamelContextHelper.mandatoryLookup(camelContext, name, Module.class);
                    LOG.debug("Registering module: {} -> {}", name, module);
                    objectMapper.registerModule(module);
                }
            }
            if (org.apache.camel.util.ObjectHelper.isNotEmpty(timezone)) {
                LOG.debug("Setting timezone to Object Mapper: {}", timezone);
                objectMapper.setTimeZone(timezone);
            }
        } else {
            LOG.info("The objectMapper was already found in the registry, no customizations will be applied");
        }
    }

};