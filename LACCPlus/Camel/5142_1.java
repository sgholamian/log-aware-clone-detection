//,temp,CxfEndpoint.java,463,572,temp,CxfEndpoint.java,258,376
//,3
public class xxx {
    protected void setupClientFactoryBean(ClientFactoryBean factoryBean, Class<?> cls) {
        if (cls != null) {
            factoryBean.setServiceClass(cls);
        }
        factoryBean.setInInterceptors(in);
        factoryBean.setOutInterceptors(out);
        factoryBean.setOutFaultInterceptors(outFault);
        factoryBean.setInFaultInterceptors(inFault);
        factoryBean.setFeatures(features);
        factoryBean.setTransportId(transportId);
        factoryBean.setBindingId(bindingId);

        if (bindingConfig != null) {
            factoryBean.setBindingConfig(bindingConfig);
        }

        if (dataBinding != null) {
            factoryBean.setDataBinding(dataBinding);
        }

        if (serviceFactoryBean != null) {
            setServiceFactory(factoryBean, serviceFactoryBean);
        }

        // address
        factoryBean.setAddress(getAddress());

        // wsdl url
        if (getWsdlURL() != null) {
            factoryBean.setWsdlURL(getWsdlURL());
        }

        // service name qname
        if (getServiceNameAsQName() != null) {
            factoryBean.setServiceName(getServiceNameAsQName());
        }

        // port name qname
        if (getPortNameAsQName() != null) {
            factoryBean.setEndpointName(getPortNameAsQName());
        }

        // apply feature here
        if (getDataFormat().dealias() == DataFormat.RAW) {
            RAWDataFormatFeature feature = new RAWDataFormatFeature();
            feature.addInIntercepters(getInInterceptors());
            feature.addOutInterceptors(getOutInterceptors());
            factoryBean.getFeatures().add(feature);
        } else if (getDataFormat().dealias() == DataFormat.CXF_MESSAGE) {
            factoryBean.getFeatures().add(new CXFMessageDataFormatFeature());
            factoryBean.setDataBinding(new SourceDataBinding());
        } else if (getDataFormat() == DataFormat.PAYLOAD) {
            factoryBean.getFeatures().add(new PayLoadDataFormatFeature(allowStreaming));
            factoryBean.setDataBinding(new HybridSourceDataBinding());
        }

        if (isLoggingFeatureEnabled()) {
            LoggingFeature loggingFeature = new LoggingFeature();
            if (getLoggingSizeLimit() >= -1) {
                loggingFeature.setLimit(getLoggingSizeLimit());

            }
            factoryBean.getFeatures().add(loggingFeature);
        }

        // set the document-literal wrapped style
        if (getWrappedStyle() != null) {
            setWrapped(factoryBean, getWrappedStyle());
        }

        // any optional properties
        if (getProperties() != null) {
            if (factoryBean.getProperties() != null) {
                // add to existing properties
                factoryBean.getProperties().putAll(getProperties());
            } else {
                factoryBean.setProperties(getProperties());
            }
            LOG.debug("ClientFactoryBean: {} added properties: {}", factoryBean, getProperties());
        }

        // setup the basic authentication property
        if (ObjectHelper.isNotEmpty(username)) {
            AuthorizationPolicy authPolicy = new AuthorizationPolicy();
            authPolicy.setUserName(username);
            authPolicy.setPassword(password);
            if (factoryBean.getProperties() == null) {
                factoryBean.setProperties(new HashMap<String, Object>());
            }
            factoryBean.getProperties().put(AuthorizationPolicy.class.getName(), authPolicy);
        }

        if (this.isSkipPayloadMessagePartCheck()) {
            if (factoryBean.getProperties() == null) {
                factoryBean.setProperties(new HashMap<String, Object>());
            }
            factoryBean.getProperties().put("soap.no.validate.parts", Boolean.TRUE);
        }

        if (this.isSkipFaultLogging()) {
            if (factoryBean.getProperties() == null) {
                factoryBean.setProperties(new HashMap<String, Object>());
            }
            factoryBean.getProperties().put(FaultListener.class.getName(), new NullFaultListener());
        }

        factoryBean.setBus(getBus());

        getNullSafeCxfConfigurer().configure(factoryBean);
    }

};