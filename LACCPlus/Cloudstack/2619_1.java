//,temp,SnapshotDescriptor.java,39,62,temp,LibvirtComputingResource.java,486,503
//,3
public class xxx {
    public void parse(byte[] vmsdFileContent) throws IOException {
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(new ByteArrayInputStream(vmsdFileContent),"UTF-8"));
            String line;
            while ((line = in.readLine()) != null) {
                // TODO, remember to remove this log, temporarily added for debugging purpose
                s_logger.info("Parse snapshot file content: " + line);

                String[] tokens = line.split("=");
                if (tokens.length == 2) {
                    String name = tokens[0].trim();
                    String value = tokens[1].trim();
                    if (value.charAt(0) == '\"')
                        value = value.substring(1, value.length() - 1);

                    _properties.put(name, value);
                }
            }
        } finally {
            if (in != null)
                in.close();
        }
    }

};