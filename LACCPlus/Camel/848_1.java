//,temp,MavenVersionManager.java,130,153,temp,MavenVersionManager.java,96,123
//,3
public class xxx {
    @Override
    public boolean loadRuntimeProviderVersion(String groupId, String artifactId, String version) {
        try {
            URLHandlerRegistry.setDefault(httpClient);

            Grape.setEnableAutoDownload(true);

            Map<String, Object> param = new HashMap<>();
            param.put("classLoader", classLoader);
            param.put("group", groupId);
            param.put("module", artifactId);
            param.put("version", version);

            Grape.grab(param);

            this.runtimeProviderVersion = version;
            return true;
        } catch (Exception e) {
            if (log) {
                LOG.warn("Cannot load runtime provider version {} due {}", version, e.getMessage(), e);
            }
            return false;
        }
    }

};