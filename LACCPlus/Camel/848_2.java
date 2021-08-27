//,temp,MavenVersionManager.java,130,153,temp,MavenVersionManager.java,96,123
//,3
public class xxx {
    @Override
    public boolean loadVersion(String version) {
        try {
            URLHandlerRegistry.setDefault(httpClient);

            if (cacheDirectory != null) {
                System.setProperty("grape.root", cacheDirectory);
            }

            Grape.setEnableAutoDownload(true);

            Map<String, Object> param = new HashMap<>();
            param.put("classLoader", classLoader);
            param.put("group", "org.apache.camel");
            param.put("module", "camel-catalog");
            param.put("version", version);

            Grape.grab(param);

            this.version = version;
            return true;
        } catch (Exception e) {
            if (log) {
                LOG.warn("Cannot load version {} due {}", version, e.getMessage(), e);
            }
            return false;
        }
    }

};