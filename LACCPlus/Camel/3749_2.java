//,temp,ZooImageClassificationPredictor.java,71,79,temp,CustomObjectDetectionPredictor.java,84,92
//,3
public class xxx {
    public DetectedObjects classify(Model model, Translator translator, InputStream input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(input);
            return classify(model, translator, image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};