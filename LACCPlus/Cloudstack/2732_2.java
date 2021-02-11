//,temp,DownloadManagerImpl.java,861,905,temp,DownloadManagerImpl.java,815,859
//,3
public class xxx {
    @Override
    public Map<String, TemplateProp> gatherTemplateInfo(String rootDir) {
        Map<String, TemplateProp> result = new HashMap<String, TemplateProp>();
        String templateDir = rootDir + File.separator + _templateDir;

        if (!_storage.exists(templateDir)) {
            _storage.mkdirs(templateDir);
        }

        List<String> publicTmplts = listTemplates(templateDir);
        for (String tmplt : publicTmplts) {
            String path = tmplt.substring(0, tmplt.lastIndexOf(File.separator));
            TemplateLocation loc = new TemplateLocation(_storage, path);
            try {
                if (!loc.load()) {
                    s_logger.warn("Post download installation was not completed for " + path);
                    // loc.purge();
                    _storage.cleanup(path, templateDir);
                    continue;
                }
            } catch (IOException e) {
                s_logger.warn("Unable to load template location " + path, e);
                continue;
            }

            TemplateProp tInfo = loc.getTemplateInfo();

            if ((tInfo.getSize() == tInfo.getPhysicalSize()) && (tInfo.getInstallPath().endsWith(ImageFormat.OVA.getFileExtension()))) {
                try {
                    Processor processor = _processors.get("OVA Processor");
                    OVAProcessor vmdkProcessor = (OVAProcessor)processor;
                    long vSize = vmdkProcessor.getTemplateVirtualSize(path, tInfo.getInstallPath().substring(tInfo.getInstallPath().lastIndexOf(File.separator) + 1));
                    tInfo.setSize(vSize);
                    loc.updateVirtualSize(vSize);
                    loc.save();
                } catch (Exception e) {
                    s_logger.error("Unable to get the virtual size of the template: " + tInfo.getInstallPath() + " due to " + e.getMessage());
                }
            }

            result.put(tInfo.getTemplateName(), tInfo);
            s_logger.debug("Added template name: " + tInfo.getTemplateName() + ", path: " + tmplt);
        }
        return result;
    }

};