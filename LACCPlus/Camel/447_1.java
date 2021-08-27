//,temp,ZooObjectDetectionPredictor.java,87,95,temp,CustomImageClassificationPredictor.java,66,74
//,3
public class xxx {
    public DetectedObjects classify(InputStream input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(input);
            return classify(image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};