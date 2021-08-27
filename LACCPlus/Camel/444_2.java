//,temp,ZooObjectDetectionPredictor.java,77,85,temp,CustomImageClassificationPredictor.java,76,84
//,3
public class xxx {
    private Map<String, Float> classify(Model model, Translator translator, InputStream input) throws Exception {
        try {
            Image image = ImageFactory.getInstance().fromInputStream(input);
            return classify(model, translator, image);
        } catch (IOException e) {
            LOG.error("Couldn't transform input into a BufferedImage");
            throw new RuntimeException("Couldn't transform input into a BufferedImage", e);
        }
    }

};