//,temp,PackageDataFormatMojo.java,125,259,temp,PackageLanguageMojo.java,97,201
//,3
public class xxx {
    public int prepareDataFormat() throws MojoExecutionException {
        Log log = getLog();

        File camelMetaDir = new File(dataFormatOutDir, "META-INF/services/org/apache/camel/");

        // first we need to setup the output directory because the next check
        // can stop the build before the end and eclipse always needs to know
        // about that directory
        if (projectHelper != null) {
            projectHelper.addResource(project, dataFormatOutDir.getPath(),
                    Collections.singletonList("**/dataformat.properties"), Collections.emptyList());
        }

        if (!haveResourcesChanged(log, project, buildContext, "META-INF/services/org/apache/camel/dataformat")) {
            return 0;
        }

        Map<String, String> javaTypes = new HashMap<>();

        StringBuilder buffer = new StringBuilder();
        int count = 0;

        File f = new File(project.getBasedir(), "target/classes");
        f = new File(f, "META-INF/services/org/apache/camel/dataformat");
        if (f.exists() && f.isDirectory()) {
            File[] files = f.listFiles();
            if (files != null) {
                for (File file : files) {
                    String javaType = readClassFromCamelResource(file, buffer, buildContext);
                    if (!file.isDirectory() && file.getName().charAt(0) != '.') {
                        count++;
                    }
                    if (javaType != null) {
                        javaTypes.put(file.getName(), javaType);
                    }
                }
            }
        }

        // is this from Apache Camel then the data format is out of the box and
        // we should enrich the json schema with more details
        boolean apacheCamel = "org.apache.camel".equals(project.getGroupId());

        // find camel-core and grab the data format model from there, and enrich
        // this model with information from this artifact
        // and create json schema model file for this data format
        try {
            if (apacheCamel && count > 0) {
                File core = PackageHelper.findCamelCoreModelDirectory(project.getBasedir());
                if (core != null) {
                    for (Map.Entry<String, String> entry : javaTypes.entrySet()) {
                        String name = entry.getKey();
                        String javaType = entry.getValue();
                        String modelName = asModelName(name);

                        String json = PackageHelper.loadText(new File(
                                core,
                                "target/classes/org/apache/camel/model/dataformat/" + modelName + PackageHelper.JSON_SUFIX));

                        // any excluded properties
                        Class<?> clazz = loadClass(javaType);
                        Metadata metadata = clazz.getAnnotation(Metadata.class);
                        String included = "";
                        String excluded = "";
                        if (metadata != null) {
                            included = metadata.includeProperties();
                            excluded = metadata.excludeProperties();
                        }

                        final DataFormatModel dataFormatModel
                                = extractDataFormatModel(project, json, name, clazz, included, excluded);
                        if (!modelName.equals(name)) {
                            /* Prefer description from the clazz */
                            setDescriptionFromClass(clazz, dataFormatModel);
                        }
                        if (log.isDebugEnabled()) {
                            log.debug("Model: " + dataFormatModel);
                        }
                        String schema = JsonMapper.createParameterJsonSchema(dataFormatModel);
                        if (log.isDebugEnabled()) {
                            log.debug("JSON schema:\n" + schema);
                        }

                        // write this to the directory
                        Path out = schemaOutDir.toPath().resolve(schemaSubDirectory(dataFormatModel.getJavaType()))
                                .resolve(name + PackageHelper.JSON_SUFIX);
                        updateResource(schemaOutDir.toPath(),
                                schemaSubDirectory(dataFormatModel.getJavaType()) + "/" + name + PackageHelper.JSON_SUFIX,
                                schema);

                        if (log.isDebugEnabled()) {
                            log.debug("Generated " + out + " containing JSON schema for " + name + " data format");
                        }

                        String cn = javaType.substring(javaType.lastIndexOf('.') + 1);
                        String pn = javaType.substring(0, javaType.length() - cn.length() - 1);
                        Set<String> names = dataFormatModel.getOptions().stream().map(DataFormatOptionModel::getName)
                                .collect(Collectors.toSet());
                        List<DataFormatOptionModel> options = parseConfigurationSource(project, javaType);
                        options.removeIf(o -> !names.contains(o.getName()));
                        names.removeAll(options.stream().map(DataFormatOptionModel::getName).collect(Collectors.toList()));
                        names.removeAll(Arrays.asList("id"));
                        if (!names.isEmpty()) {
                            log.warn("Unmapped options: " + String.join(",", names));
                        }
                        updateResource(configurerSourceOutDir.toPath(),
                                pn.replace('.', '/') + "/" + cn + "Configurer.java",
                                generatePropertyConfigurer(pn, cn + "Configurer", cn, options));
                        updateResource(configurerResourceOutDir.toPath(),
                                "META-INF/services/org/apache/camel/configurer/" + name + "-dataformat",
                                generateMetaInfConfigurer(pn + "." + cn + "Configurer"));
                    }
                } else {
                    throw new MojoExecutionException(
                            "Error finding core/camel-core/target/camel-core-model-" + project.getVersion()
                                                     + ".jar file. Make sure camel-core has been built first.");
                }
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Error loading dataformat model from camel-core. Reason: " + e, e);
        }

        if (count > 0) {
            String names = Stream.of(buffer.toString().split(" ")).sorted().collect(Collectors.joining(" "));
            String properties = createProperties(project, "dataFormats", names);
            updateResource(camelMetaDir.toPath(), "dataformat.properties", properties);
            log.info("Generated dataformat.properties containing " + count + " Camel "
                     + (count > 1 ? "dataformats: " : "dataformat: ") + names);
        } else {
            log.debug(
                    "No META-INF/services/org/apache/camel/dataformat directory found. Are you sure you have created a Camel data format?");
        }

        return count;
    }

};