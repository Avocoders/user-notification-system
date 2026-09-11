package io.github.avocoders.notificationservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.listener.DeadLetterPublishingRecoverer;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.util.backoff.FixedBackOff;

@Configuration
@RequiredArgsConstructor
public class KafkaConsumerConfig {
    private final KafkaTemplate<Object, Object> kafkaTemplate;

    @Bean
    public DeadLetterPublishingRecoverer deadLetterPublishingRecoverer() {

        return new DeadLetterPublishingRecoverer(kafkaTemplate);
    }

    @Bean
    public DefaultErrorHandler defaultErrorHandler(DeadLetterPublishingRecoverer deadLetterPublishingRecoverer) {
        FixedBackOff fixedBackOff = new FixedBackOff(2000L, 2L);

        return new DefaultErrorHandler(deadLetterPublishingRecoverer, fixedBackOff);
    }
}
