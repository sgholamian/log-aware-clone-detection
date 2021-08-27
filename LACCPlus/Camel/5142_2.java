//,temp,CxfEndpoint.java,463,572,temp,CxfEndpoint.java,258,376
//,3
public class xxx {
    protected void setupServerFactoryBean(ServerFactoryBean sfb, Class<?> cls) {

        // address
        sfb.setAddress(getAddress());

        sfb.setServiceClass(cls);

        sfb.setInInterceptors(in);
        sfb.setOutInterceptors(out);
        sfb.setOutFaultInterceptors(outFault);
        sfb.setInFaultInterceptors(inFault);
        sfb.setFeatures(features);
        if (schemaLocations != null) {
            sfb.setSchemaLocations(schemaLocations);
        }
        if (bindingConfig != null) {
            sfb.setBindingConfig(bindingConfig);
        }

        if (dataBinding != null) {
            sfb.setDataBinding(dataBinding);
        }

        if (serviceFactoryBean != null) {
            setServiceFactory(sfb, serviceFactoryBean);
        }

        if (sfb instanceof JaxWsServerFactoryBean && handlers != null) {
            ((JaxWsServerFactoryBean) sfb).setHandlers(handlers);
        }
        if (getTransportId() != null) {
            sfb.setTransportId(getTransportId());
        }
        if (getBindingId() != null) {
            sfb.setBindingId(getBindingId());
        }

        // wsdl url
        if (getWsdlURL() != null) {
            sfb.setWsdlURL(getWsdlURL());
        }

        // service name qname
        if (getServiceNameAsQName() != null) {
            sfb.setServiceName(getServiceNameAsQName());
        }

        // port qname
        if (getPortNameAsQName() != null) {
            sfb.setEndpointName(getPortNameAsQName());
        }

        // apply feature here
        if (!CxfEndpointUtils.hasAnnotation(cls, WebServiceProvider.class)) {
            if (getDataFormat() == DataFormat.PAYLOAD) {
                sfb.getFeatures().add(new PayLoadDataFormatFeature(allowStreaming));
            } else if (getDataFormat().dealias() == DataFormat.CXF_MESSAGE) {
                sfb.getFeatures().add(new CXFMessageDataFormatFeature());
                sfb.setDataBinding(new SourceDataBinding());
            } else if (getDataFormat().dealias() == DataFormat.RAW) {
                RAWDataFormatFeature feature = new RAWDataFormatFeature();
                if (this.getExchangePattern().equals(ExchangePattern.InOnly)) {
                    //if DataFormat is RAW|MESSAGE, can't read message so can't
                    //determine it's oneway so need get the MEP from URI explicitly
                    feature.setOneway(true);
                }
                feature.addInIntercepters(getInInterceptors());
                feature.addOutInterceptors(getOutInterceptors());
                sfb.getFeatures().add(feature);
            }
        } else {
            LOG.debug("Ignore DataFormat mode {} since SEI class is annotated with WebServiceProvider", getDataFormat());
        }

        if (isLoggingFeatureEnabled()) {
            LoggingFeature loggingFeature = new LoggingFeature();
            if (getLoggingSizeLimit() >= -1) {
                loggingFeature.setLimit(getLoggingSizeLimit());
            }
            sfb.getFeatures().add(loggingFeature);
        }

        if (getDataFormat() == DataFormat.PAYLOAD) {
            sfb.setDataBinding(new HybridSourceDataBinding());
        }

        // set the document-literal wrapped style
        if (getWrappedStyle() != null && getDataFormat().dealias() != DataFormat.CXF_MESSAGE) {
            setWrapped(sfb, getWrappedStyle());
        }

        // any optional properties
        if (getProperties() != null) {
            if (sfb.getProperties() != null) {
                // add to existing properties
                sfb.getProperties().putAll(getProperties());
            } else {
                sfb.setProperties(getProperties());
            }
            LOG.debug("ServerFactoryBean: {} added properties: {}", sfb, getProperties());
        }
        if (this.isSkipPayloadMessagePartCheck()) {
            if (sfb.getProperties() == null) {
                sfb.setProperties(new HashMap<String, Object>());
            }
            sfb.getProperties().put("soap.no.validate.parts", Boolean.TRUE);
        }

        if (this.isSkipFaultLogging()) {
            if (sfb.getProperties() == null) {
                sfb.setProperties(new HashMap<String, Object>());
            }
            sfb.getProperties().put(FaultListener.class.getName(), new NullFaultListener());
        }

        sfb.setBus(getBus());
        sfb.setStart(false);
        getNullSafeCxfConfigurer().configure(sfb);
    }

};