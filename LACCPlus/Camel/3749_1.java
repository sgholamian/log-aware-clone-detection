//,temp,ZooImageClassificationPredictor.java,71,79,temp,CustomObjectDetectionPredictor.java,84,92
//,3
public class xxx {
    public Map<String, Float> classify(File input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(new FileInputStream(input));
            return classify(image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};