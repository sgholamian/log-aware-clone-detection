//,temp,NfsSecondaryStorageResource.java,3195,3320,temp,DownloadManagerImpl.java,365,484
//,3
public class xxx {
    private String postLocalDownload(String jobId) {
        DownloadJob dnld = jobs.get(jobId);
        TemplateDownloader td = dnld.getTemplateDownloader();
        String resourcePath = dnld.getInstallPathPrefix(); // path with mount
        // directory
        String finalResourcePath = dnld.getTmpltPath(); // template download
        // path on secondary
        // storage
        ResourceType resourceType = dnld.getResourceType();

        File originalTemplate = new File(td.getDownloadLocalPath());
        ChecksumValue oldValue = new ChecksumValue(dnld.getChecksum());
        ChecksumValue newValue = null;
        try {
            newValue = computeCheckSum(oldValue.getAlgorithm(), originalTemplate);
        } catch (NoSuchAlgorithmException e) {
            return "checksum algorithm not recognised: " + oldValue.getAlgorithm();
        }
        if(StringUtils.isNotBlank(dnld.getChecksum()) && ! oldValue.equals(newValue)) {
            return "checksum \"" + newValue +"\" didn't match the given value, \"" + oldValue + "\"";
        }
        String checksum = newValue.getChecksum();
        if (checksum == null) {
            s_logger.warn("Something wrong happened when try to calculate the checksum of downloaded template!");
        }

        dnld.setCheckSum(checksum);

        int imgSizeGigs = (int)Math.ceil(_storage.getSize(td.getDownloadLocalPath()) * 1.0d / (1024 * 1024 * 1024));
        imgSizeGigs++; // add one just in case
        long timeout = (long)imgSizeGigs * installTimeoutPerGig;
        Script scr = null;
        String script = resourceType == ResourceType.TEMPLATE ? createTmpltScr : createVolScr;
        scr = new Script(script, timeout, s_logger);
        scr.add("-s", Integer.toString(imgSizeGigs));
        scr.add("-S", Long.toString(td.getMaxTemplateSizeInBytes()));
        if (dnld.getDescription() != null && dnld.getDescription().length() > 1) {
            scr.add("-d", dnld.getDescription());
        }
        if (dnld.isHvm()) {
            scr.add("-h");
        }

        // add options common to ISO and template
        String extension = dnld.getFormat().getFileExtension();
        String templateName = "";
        if (extension.equals("iso")) {
            templateName = jobs.get(jobId).getTmpltName().trim().replace(" ", "_");
        } else {
            templateName = java.util.UUID.nameUUIDFromBytes((jobs.get(jobId).getTmpltName() + System.currentTimeMillis()).getBytes(StringUtils.getPreferredCharset())).toString();
        }

        // run script to mv the temporary template file to the final template
        // file
        String templateFilename = templateName + "." + extension;
        dnld.setTmpltPath(finalResourcePath + "/" + templateFilename);
        scr.add("-n", templateFilename);

        scr.add("-t", resourcePath);
        scr.add("-f", td.getDownloadLocalPath()); // this is the temporary template file downloaded
        scr.add("-u"); // cleanup
        String result;
        result = scr.execute();

        if (result != null) {
            return result;
        }

        // Set permissions for the downloaded template
        File downloadedTemplate = new File(resourcePath + "/" + templateFilename);
        _storage.setWorldReadableAndWriteable(downloadedTemplate);

        // Set permissions for template/volume.properties
        String propertiesFile = resourcePath;
        if (resourceType == ResourceType.TEMPLATE) {
            propertiesFile += "/template.properties";
        } else {
            propertiesFile += "/volume.properties";
        }
        File templateProperties = new File(propertiesFile);
        _storage.setWorldReadableAndWriteable(templateProperties);

        TemplateLocation loc = new TemplateLocation(_storage, resourcePath);
        try {
            loc.create(dnld.getId(), true, dnld.getTmpltName());
        } catch (IOException e) {
            s_logger.warn("Something is wrong with template location " + resourcePath, e);
            loc.purge();
            return "Unable to download due to " + e.getMessage();
        }

        Iterator<Processor> en = _processors.values().iterator();
        while (en.hasNext()) {
            Processor processor = en.next();

            FormatInfo info = null;
            try {
                info = processor.process(resourcePath, null, templateName, this._processTimeout);
            } catch (InternalErrorException e) {
                s_logger.error("Template process exception ", e);
                return e.toString();
            }
            if (info != null) {
                if(!loc.addFormat(info)) {
                    loc.purge();
                    return "Unable to install due to invalid file format";
                }
                dnld.setTemplatesize(info.virtualSize);
                dnld.setTemplatePhysicalSize(info.size);
                break;
            }
        }

        if (!loc.save()) {
            s_logger.warn("Cleaning up because we're unable to save the formats");
            loc.purge();
        }

        return null;
    }

};