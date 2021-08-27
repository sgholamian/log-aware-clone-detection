//,temp,AbstractCamelContext.java,1654,1679,temp,AbstractCamelContext.java,1626,1652
//,2
public class xxx {
    public String getLanguageParameterJsonSchema(String languageName) throws IOException {
        // use the language factory finder to find the package name of the
        // language class, which is the location
        // where the documentation exists as well
        FactoryFinder finder = getFactoryFinder(DefaultLanguageResolver.LANGUAGE_RESOURCE_PATH);
        Class<?> clazz = finder.findClass(languageName).orElse(null);
        if (clazz == null) {
            return null;
        }

        String packageName = clazz.getPackage().getName();
        packageName = packageName.replace('.', '/');
        String path = packageName + "/" + languageName + ".json";

        ClassResolver resolver = getClassResolver();
        InputStream inputStream = resolver.loadResourceAsStream(path);
        LOG.debug("Loading language JSON Schema for: {} using class resolver: {} -> {}", languageName, resolver, inputStream);
        if (inputStream != null) {
            try {
                return IOHelper.loadText(inputStream);
            } finally {
                IOHelper.close(inputStream);
            }
        }
        return null;
    }

};