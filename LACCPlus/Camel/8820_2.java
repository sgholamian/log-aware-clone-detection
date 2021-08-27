//,temp,BaseEmbeddedKafkaTestSupport.java,57,67,temp,BaseEmbeddedKafkaTest.java,57,67
//,3
public class xxx {
    protected Properties getDefaultProperties() {
        LOG.info("Connecting to Kafka {}", service.getBootstrapServers());

        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, service.getBootstrapServers());
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        props.put(ProducerConfig.PARTITIONER_CLASS_CONFIG, DefaultPartitioner.class);
        props.put(ProducerConfig.ACKS_CONFIG, "1");
        return props;
    }

};