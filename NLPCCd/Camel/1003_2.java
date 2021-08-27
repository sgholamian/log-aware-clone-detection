//,temp,sample_5280.java,2,15,temp,sample_5279.java,2,13
//,3
public class xxx {
public void run() {
consumer.subscribe(Collections.singleton(topic));
consumer.seekToBeginning(consumer.assignment());
POLL_LOOP: while (running.get()) {
ConsumerRecords<String, String> consumerRecords = consumer.poll(pollDurationMs);
if (consumerRecords.isEmpty()) {


log.info("messages fetched on poll");
}
}
}

};