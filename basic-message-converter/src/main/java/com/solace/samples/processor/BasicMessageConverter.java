package com.solace.samples.processor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.cloud.stream.messaging.Processor;
import org.springframework.context.annotation.Bean;

import org.springframework.messaging.Message;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.support.MessageBuilder;

import com.solace.samples.converters.PlainTextMessageConverter;

@SpringBootApplication
@EnableBinding(Processor.class)
public class BasicMessageConverter {

    @Bean
    public MessageConverter providesPlainTextMessageConverter() {
        return new PlainTextMessageConverter();
    }

    @StreamListener(Processor.INPUT)
    @SendTo(Processor.OUTPUT)
    public <T> Message<String> enrichLogMessage(Message<T> msg) {
    	return message(String.format("[1]: %s", msg.getPayload().toString()));
    }
    
    private static final <T> Message<T> message(T val) {
        return MessageBuilder.withPayload(val).build();
    }

    public static void main(String[] args) {
        SpringApplication.run(BasicMessageConverter.class, args);
    }
    
}
