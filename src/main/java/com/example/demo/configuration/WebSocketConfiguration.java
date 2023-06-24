package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfiguration implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic");
        config.setApplicationDestinationPrefixes("/app");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket").setAllowedOrigins("*").withSockJS();
    }
}

// The message broker receives messages from publishers and routes them to the appropriate subscribers based on the specified destinations. It may also perform additional tasks such as message transformation, filtering, and buffering.
// @EnableWebSocketMessageBroker is used to enable WebSocket message handling with a message broker.

// By invoking config.enableStompBrokerRelay(), you are configuring your application to use a STOMP broker relay as the message broker. A STOMP broker relay is responsible for routing messages between clients and servers in a distributed messaging system. When using the STOMP broker relay, your application will rely on an external message broker (such as RabbitMQ or ActiveMQ).
// config.enableSimpleBroker() is used to enable a simple in-memory message broker for message routing.
// "In-memory" in the context of a message broker refers to storing and managing the message routing information in the memory of the application, rather than persisting it in a separate data store or using an external messaging system. it means that the message routing information, including the list of active WebSocket sessions and the subscriptions to various topics, is stored in the memory of your application.

// By config.setApplicationDestinationPrefixes("/app") you specify a common prefix for the destinations to which clients will send messages.

// registerStompEndpoints method is used to register WebSocket endpoints for STOMP (Simple Text Oriented Messaging Protocol) communication. These endpoints are responsible for handling WebSocket communication and enabling clients to establish a WebSocket connection.
// we do not use registerStompEndpoints method when using config.enableStompBrokerRelay as the relay handles the message routing and client connections.

// registry.addEndpoint("/websocket") method is used to register a WebSocket endpoint. Clients can connect to this endpoint to establish a WebSocket connection.
// setAllowedOrigins("*") method allows connections from any origin.
// withSockJS() method enables fallback options for browsers that do not support native WebSocket. SockJS is a JavaScript library that provides a WebSocket-like object for fallback transports like HTTP long-polling.