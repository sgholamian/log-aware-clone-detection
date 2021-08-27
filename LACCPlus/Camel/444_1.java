//,temp,ZooObjectDetectionPredictor.java,77,85,temp,CustomImageClassificationPredictor.java,76,84
//,3
public class xxx {
    public DetectedObjects classify(File input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(new FileInputStream(input));
            return classify(image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};