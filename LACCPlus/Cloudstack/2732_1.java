//,temp,DownloadManagerImpl.java,861,905,temp,DownloadManagerImpl.java,815,859
//,3
public class xxx {
    @Override
    public Map<Long, TemplateProp> gatherVolumeInfo(String rootDir) {
        Map<Long, TemplateProp> result = new HashMap<Long, TemplateProp>();
        String volumeDir = rootDir + File.separator + _volumeDir;

        if (!_storage.exists(volumeDir)) {
            _storage.mkdirs(volumeDir);
        }

        List<String> vols = listVolumes(volumeDir);
        for (String vol : vols) {
            String path = vol.substring(0, vol.lastIndexOf(File.separator));
            TemplateLocation loc = new TemplateLocation(_storage, path);
            try {
                if (!loc.load()) {
                    s_logger.warn("Post download installation was not completed for " + path);
                    // loc.purge();
                    _storage.cleanup(path, volumeDir);
                    continue;
                }
            } catch (IOException e) {
                s_logger.warn("Unable to load volume location " + path, e);
                continue;
            }

            TemplateProp vInfo = loc.getTemplateInfo();

            if ((vInfo.getSize() == vInfo.getPhysicalSize()) && (vInfo.getInstallPath().endsWith(ImageFormat.OVA.getFileExtension()))) {
                try {
                    Processor processor = _processors.get("OVA Processor");
                    OVAProcessor vmdkProcessor = (OVAProcessor)processor;
                    long vSize = vmdkProcessor.getTemplateVirtualSize(path, vInfo.getInstallPath().substring(vInfo.getInstallPath().lastIndexOf(File.separator) + 1));
                    vInfo.setSize(vSize);
                    loc.updateVirtualSize(vSize);
                    loc.save();
                } catch (Exception e) {
                    s_logger.error("Unable to get the virtual size of the volume: " + vInfo.getInstallPath() + " due to " + e.getMessage());
                }
            }

            result.put(vInfo.getId(), vInfo);
            s_logger.debug("Added volume name: " + vInfo.getTemplateName() + ", path: " + vol);
        }
        return result;
    }

};