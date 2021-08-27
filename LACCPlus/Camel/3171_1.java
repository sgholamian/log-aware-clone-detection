//,temp,ZooImageClassificationPredictor.java,81,89,temp,CustomObjectDetectionPredictor.java,74,82
//,3
public class xxx {
    public Map<String, Float> classify(InputStream input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(input);
            return classify(image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};