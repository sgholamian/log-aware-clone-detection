//,temp,PackageDataFormatMojo.java,125,259,temp,PackageLanguageMojo.java,97,201
//,3
public class xxx {
    public int prepareLanguage() throws MojoExecutionException {
        Log log = getLog();

        File camelMetaDir = new File(languageOutDir, "META-INF/services/org/apache/camel/");

        // first we need to setup the output directory because the next check
        // can stop the build before the end and eclipse always needs to know
        // about that directory
        if (projectHelper != null) {
            projectHelper.addResource(project, languageOutDir.getPath(), Collections.singletonList("**/language.properties"),
                    Collections.emptyList());
        }

        if (!haveResourcesChanged(log, project, buildContext, "META-INF/services/org/apache/camel/language")) {
            return 0;
        }

        Map<String, String> javaTypes = new HashMap<>();

        StringBuilder buffer = new StringBuilder();
        int count = 0;

        // TODO
        File f = new File(project.getBasedir(), "target/classes");
        f = new File(f, "META-INF/services/org/apache/camel/language");
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

        // find camel-core and grab the language model from there, and enrich
        // this model with information from this artifact
        // and create json schema model file for this language
        try {
            if (apacheCamel && count > 0) {
                File core = PackageHelper.findCamelCoreModelDirectory(project.getBasedir());
                if (core != null) {
                    for (Map.Entry<String, String> entry : javaTypes.entrySet()) {
                        String name = entry.getKey();
                        Class<?> javaType = loadClass(entry.getValue());
                        String modelName = asModelName(name);

                        String json = PackageHelper.loadText(new File(
                                core, "src/generated/resources/org/apache/camel/model/language/" + modelName
                                      + PackageHelper.JSON_SUFIX));

                        LanguageModel languageModel = extractLanguageModel(project, json, name, javaType);
                        if (log.isDebugEnabled()) {
                            log.debug("Model: " + languageModel);
                        }

                        // build json schema for the data format
                        String schema = JsonMapper.createParameterJsonSchema(languageModel);
                        if (log.isDebugEnabled()) {
                            log.debug("JSON schema\n" + schema);
                        }

                        // write this to the directory
                        Path out = schemaOutDir.toPath().resolve(schemaSubDirectory(languageModel.getJavaType()))
                                .resolve(name + PackageHelper.JSON_SUFIX);
                        updateResource(schemaOutDir.toPath(),
                                schemaSubDirectory(languageModel.getJavaType()) + "/" + name + PackageHelper.JSON_SUFIX,
                                schema);

                        if (log.isDebugEnabled()) {
                            log.debug("Generated " + out + " containing JSON schema for " + name + " language");
                        }
                    }
                } else {
                    throw new MojoExecutionException(
                            "Error finding core/camel-core/target/camel-core-model-" + project.getVersion()
                                                     + ".jar file. Make sure camel-core has been built first.");
                }
            }
        } catch (Exception e) {
            throw new MojoExecutionException("Error loading language model from camel-core. Reason: " + e, e);
        }

        if (count > 0) {
            String names = Stream.of(buffer.toString().split(" ")).sorted().collect(Collectors.joining(" "));
            String properties = createProperties(project, "languages", names);
            updateResource(camelMetaDir.toPath(), "language.properties", properties);
            log.info("Generated language.properties containing " + count + " Camel "
                     + (count > 1 ? "languages: " : "language: ") + names);
        } else {
            log.debug(
                    "No META-INF/services/org/apache/camel/language directory found. Are you sure you have created a Camel language?");
        }

        return count;
    }

};