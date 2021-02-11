//,temp,ConsoleProxyMonitor.java,44,57,temp,LibvirtComputingResource.java,486,503
//,3
public class xxx {
        @Override
        public String interpret(final BufferedReader reader) throws IOException {
            String line = null;
            int numLines = 0;
            while ((line = reader.readLine()) != null) {
                final String[] toks = line.trim().split("=");
                if (toks.length < 2) {
                    s_logger.warn("Failed to parse Script output: " + line);
                } else {
                    map.put(toks[0].trim(), toks[1].trim());
                }
                numLines++;
            }
            if (numLines == 0) {
                s_logger.warn("KeyValueInterpreter: no output lines?");
            }
            return null;
        }

};