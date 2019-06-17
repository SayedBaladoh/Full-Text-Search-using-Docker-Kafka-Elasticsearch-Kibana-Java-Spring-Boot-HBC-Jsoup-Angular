package com.sayedbaladoh.buzzdiggr.producer.config;

import com.sayedbaladoh.buzzdiggr.producer.model.Tweet;
import com.sayedbaladoh.buzzdiggr.producer.model.Article;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * Kakfa Producer Configuration
 * 
 * @author SayedBaladoh
 *
 */
@Configuration
public class KakfaProducerConfig {

	@Value("${kafka.boot.server}")
	private String kafkaServer;
	
	// String Producer Factory
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	// String Kafka Template
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}
	
	
	// Tweet Producer Factory
	@Bean
	public ProducerFactory<String, Tweet> tweetProducerFactory() {
		
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	// Tweet Kafka Template
	@Bean
	public KafkaTemplate<String, Tweet> tweetKafkaTemplate() {
		return new KafkaTemplate<>(tweetProducerFactory());
	}
	
	// Article Producer Factory
	@Bean
	public ProducerFactory<String, Article> articleProducerFactory() {
		
		Map<String, Object> config = new HashMap<>();

		config.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
		config.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		config.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);

		return new DefaultKafkaProducerFactory<>(config);
	}

	// Article Kafka Template
	@Bean
	public KafkaTemplate<String, Article> articleKafkaTemplate() {
		return new KafkaTemplate<>(articleProducerFactory());
	}
}
