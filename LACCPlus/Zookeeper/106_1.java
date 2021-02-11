//,temp,SaslQuorumServerCallbackHandler.java,52,77,temp,SaslServerCallbackHandler.java,49,74
//,3
public class xxx {
    public SaslQuorumServerCallbackHandler(Configuration configuration,
            String serverSection, Set<String> authzHosts) throws IOException {
        AppConfigurationEntry configurationEntries[] = configuration.getAppConfigurationEntry(serverSection);

        if (configurationEntries == null) {
            String errorMessage = "Could not find a '" + serverSection + "' entry in this configuration: Server cannot start.";
            LOG.error(errorMessage);
            throw new IOException(errorMessage);
        }
        credentials.clear();
        for(AppConfigurationEntry entry: configurationEntries) {
            Map<String,?> options = entry.getOptions();
            // Populate DIGEST-MD5 user -> password map with JAAS configuration entries from the "QuorumServer" section.
            // Usernames are distinguished from other options by prefixing the username with a "user_" prefix.
            for(Map.Entry<String, ?> pair : options.entrySet()) {
                String key = pair.getKey();
                if (key.startsWith(USER_PREFIX)) {
                    String userName = key.substring(USER_PREFIX.length());
                    credentials.put(userName,(String)pair.getValue());
                }
            }
        }

        // authorized host lists
        this.authzHosts = authzHosts;
    }

};