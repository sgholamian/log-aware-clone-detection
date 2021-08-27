//,temp,CSimpleLanguage.java,316,371,temp,JoorLanguage.java,139,187
//,3
public class xxx {
    private void loadConfiguration() {
        // attempt to load configuration
        String loaded = ScriptHelper.resolveOptionalExternalScript(getCamelContext(), "resource:" + configResource);
        int counter1 = 0;
        int counter2 = 0;
        if (loaded != null) {
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
        }
        if (counter1 > 0 || counter2 > 0) {
            LOG.info("Loaded jOOR language imports: {} and aliases: {} from configuration: {}", counter1, counter2,
                    configResource);
        }
        if (compiler.getAliases() == null) {
            compiler.setAliases(aliases);
        } else {
            compiler.getAliases().putAll(aliases);
        }
        if (compiler.getImports() == null) {
            compiler.setImports(imports);
        } else {
            compiler.getImports().addAll(imports);
        }
    }

};