//,temp,CSimpleLanguage.java,316,371,temp,JoorLanguage.java,139,187
//,3
public class xxx {
        private void loadConfiguration() {
            InputStream is;
            String loaded;
            is = getCamelContext().getClassResolver().loadResourceAsStream(CONFIG_FILE);
            try {
                if (is == null) {
                    // load from file system
                    File file = new File(configResource);
                    if (file.exists()) {
                        is = new FileInputStream(file);
                    }
                }
                if (is == null) {
                    return;
                }
                loaded = IOHelper.loadText(is);
            } catch (IOException e) {
                throw new RuntimeCamelException("Cannot load " + CONFIG_FILE + " from classpath");

            }
            IOHelper.close(is);

            int counter1 = 0;
            int counter2 = 0;
            String[] lines = loaded.split("\n");
            for (String line : lines) {
                line = line.trim();
                // skip comments
                if (line.startsWith("#")) {
                    continue;
                }
                // imports
                if (line.startsWith("import ")) {
                    imports.add(line);
                    counter1++;
                    continue;
                }
                // aliases as key=value
                String key = StringHelper.before(line, "=");
                String value = StringHelper.after(line, "=");
                if (key != null) {
                    key = key.trim();
                }
                if (value != null) {
                    value = value.trim();
                }
                if (key != null && value != null) {
                    this.aliases.put(key, value);
                    counter2++;
                }
            }
            if (counter1 > 0 || counter2 > 0) {
                LOG.info("Loaded csimple language imports: {} and aliases: {} from configuration: {}", counter1, counter2,
                        configResource);
            }
        }

};