//,temp,JmxRestDefinitionsResolver.java,48,80,temp,RestSwaggerSupport.java,167,197
//,3
public class xxx {
    @Override
    public List<RestDefinition> getRestDefinitions(CamelContext camelContext, String camelId) throws Exception {
        ObjectName found = null;

        MBeanServer server = ManagementFactory.getPlatformMBeanServer();
        Set<ObjectName> names = server.queryNames(new ObjectName("org.apache.camel:type=context,*"), null);
        for (ObjectName on : names) {
            String id = on.getKeyProperty("name");
            if (id.startsWith("\"") && id.endsWith("\"")) {
                id = id.substring(1, id.length() - 1);
            }
            if (camelId == null || camelId.equals(id)) {
                found = on;
            }
        }

        if (found != null) {
            String xml = (String) server.invoke(found, "dumpRestsAsXml", new Object[] { true },
                    new String[] { "boolean" });
            if (xml != null) {
                LOG.debug("DumpRestAsXml:\n{}", xml);
                InputStream xmlis = camelContext.getTypeConverter().convertTo(InputStream.class, xml);
                ExtendedCamelContext ecc = camelContext.adapt(ExtendedCamelContext.class);
                RestsDefinition rests
                        = (RestsDefinition) ecc.getXMLRoutesDefinitionLoader().loadRestsDefinition(camelContext, xmlis);
                if (rests != null) {
                    return rests.getRests();
                }
            }
        }

        return null;
    }

};