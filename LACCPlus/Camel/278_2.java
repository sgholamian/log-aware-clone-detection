//,temp,AbstractCamelContext.java,1654,1679,temp,AbstractCamelContext.java,1626,1652
//,2
public class xxx {
    public String getDataFormatParameterJsonSchema(String dataFormatName) throws IOException {
        // use the dataformat factory finder to find the package name of the
        // dataformat class, which is the location
        // where the documentation exists as well
        FactoryFinder finder = getFactoryFinder(DefaultDataFormatResolver.DATAFORMAT_RESOURCE_PATH);
        Class<?> clazz = finder.findClass(dataFormatName).orElse(null);
        if (clazz == null) {
            return null;
        }

        String packageName = clazz.getPackage().getName();
        packageName = packageName.replace('.', '/');
        String path = packageName + "/" + dataFormatName + ".json";

        ClassResolver resolver = getClassResolver();
        InputStream inputStream = resolver.loadResourceAsStream(path);
        LOG.debug("Loading dataformat JSON Schema for: {} using class resolver: {} -> {}", dataFormatName, resolver,
                inputStream);
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