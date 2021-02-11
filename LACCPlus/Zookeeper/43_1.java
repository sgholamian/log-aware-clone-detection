//,temp,JMXEnv.java,229,263,temp,JMXEnv.java,101,134
//,3
public class xxx {
    public static Set<ObjectName> ensureParent(String... expectedNames)
            throws IOException, InterruptedException {
        LOG.info("ensureParent:" + Arrays.toString(expectedNames));

        Set<ObjectName> beans;
        int nTry = 0;
        Set<ObjectName> found = new HashSet<ObjectName>();
        do {
            if (nTry++ > 0) {
                Thread.sleep(500);
            }
            try {
                beans = conn().queryNames(
                        new ObjectName(CommonNames.DOMAIN + ":*"), null);
            } catch (MalformedObjectNameException e) {
                throw new RuntimeException(e);
            }
            found.clear();
            for (String name : expectedNames) {
                LOG.info("expect:" + name);
                for (ObjectName bean : beans) {
                    // check the existence of name in bean
                    if (compare(bean.toString(), name)) {
                        LOG.info("found:" + name + " " + bean);
                        found.add(bean);
                        break;
                    }
                }
                beans.removeAll(found);
            }
        } while (expectedNames.length != found.size() && nTry < 120);
        Assert.assertEquals("expected " + Arrays.toString(expectedNames),
                expectedNames.length, found.size());
        return beans;
    }

};