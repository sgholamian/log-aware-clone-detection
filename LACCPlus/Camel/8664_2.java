//,temp,ZooImageClassificationPredictor.java,91,101,temp,CustomImageClassificationPredictor.java,86,96
//,3
public class xxx {
    private Map<String, Float> classify(Model model, Translator translator, Image image) throws Exception {
        try (Predictor<Image, Classifications> predictor = model.newPredictor(translator)) {
            Classifications classifications = predictor.predict(image);
            List<Classifications.Classification> list = classifications.items();
            return list.stream()
                    .collect(Collectors.toMap(Classifications.Classification::getClassName, x -> (float) x.getProbability()));
        } catch (TranslateException e) {
            LOG.error("Could not process input or output", e);
            throw new RuntimeException("Could not process input or output", e);
        }
    }

};