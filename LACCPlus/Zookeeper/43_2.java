//,temp,JMXEnv.java,229,263,temp,JMXEnv.java,101,134
//,3
public class xxx {
    public static Set<ObjectName> ensureAll(String... expectedNames)
        throws IOException, InterruptedException
    {
        Set<ObjectName> beans;
        Set<ObjectName> found;
        int nTry = 0;
        do {
            if (nTry++ > 0) {
                Thread.sleep(100);
            }
            try {
                beans = conn().queryNames(
                        new ObjectName(CommonNames.DOMAIN + ":*"), null);
            } catch (MalformedObjectNameException e) {
                throw new RuntimeException(e);
            }
        
            found = new HashSet<ObjectName>();
            for (String name : expectedNames) {
                LOG.info("expect:" + name);
                for (ObjectName bean : beans) {
                    if (bean.toString().contains(name)) {
                        LOG.info("found:" + name + " " + bean);
                        found.add(bean);
                        break;
                    }
                }
                beans.removeAll(found);
            }
        } while ((expectedNames.length != found.size()) && (nTry < 600));
        Assert.assertEquals("expected " + Arrays.toString(expectedNames),
                expectedNames.length, found.size());
        return beans;
    }

};