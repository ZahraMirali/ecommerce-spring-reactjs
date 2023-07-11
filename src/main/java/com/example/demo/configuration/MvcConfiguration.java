package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MvcConfiguration implements WebMvcConfigurer {

    @Value("${hostname}")
    private String hostname;

    @Bean
    public RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
                .addResourceLocations("classpath:/static/");
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/v1/**")
                .allowedOrigins("http://" + hostname)
                .allowedMethods("HEAD", "OPTIONS", "GET", "POST", "PUT", "PATCH", "DELETE")
                .exposedHeaders("page-total-count")
                .exposedHeaders("page-total-elements")
                .allowedHeaders("*");
    }
}

// WebMvcConfigurer is an interface provided by the Spring MVC framework that allows you to customize and configure various aspects of the MVC configuration in a Spring Boot application. It provides callback methods that you can override to add customizations to the Spring MVC configuration. The inclusion of "Mvc" in WebMvcConfigurer indicates that the interface is specifically related to the MVC (Model-View-Controller) pattern and the configuration of the Spring MVC framework. It signifies that the methods in this interface are focused on handling web requests, managing views, and interacting with models within the context of an MVC architecture.
// 1. Model-related methods: addFormatters: Customize the conversion and formatting of model attribute values. addArgumentResolvers: Customize the handling of method arguments for model processing. addReturnValueHandlers: Customize the handling of method return values for model processing.
// 2. View-related methods: configureViewResolvers: Configure the view resolvers to resolve view names to actual views. addViewControllers: Add simple view controllers for direct view mappings without a controller. addResourceHandlers: Configure resource handling for serving static resources such as images, CSS files, etc.
// 3. Controller-related methods: addInterceptors: Add interceptors to handle requests before they reach the controller. addCorsMappings: Configure Cross-Origin Resource Sharing (CORS) for controller endpoints. configureHandlerExceptionResolvers: Configure the exception resolvers for handling controller exceptions.

// ResourceHandlerRegistry: During runtime, when a request is made for a static resource (such as /static/css/style.css) that matches one of the registered URL patterns, the framework automatically routes the request to the appropriate resource handler to serve the static resource.
// addResourceHandler is used to define the URL pattern(s) for which the resource handler should be applied. The path pattern(s) can contain wildcards (* and **) to match multiple URLs. For example, "/static/**" would match any URL starting with /static/
// addResourceLocations is used to specify the location(s) where the static resources are stored. The resource locations can be specified as classpath directories ("classpath:/path/to/resources/") or file system directories ("file:/path/to/resources/")

// addMapping method configures CORS for requests that match the /api/v1/** URL pattern
// allowedOrigins specifies the allowed origins (domains) that are allowed to make cross-origin requests to the server
// allowedMethods specifies the HTTP methods that are allowed for the cross-origin requests
// exposedHeaders specifies the custom response headers that are exposed to the client. Exposed headers are additional headers that the client can access in the response.
// allowedHeaders specifies the allowed headers that the client can include in the cross-origin requests.

// Using RestTemplate, you can make HTTP requests to external APIs or services, retrieve responses, and process the data returned by those APIs. It abstracts away much of the low-level details of making HTTP requests and provides a higher-level API for working with RESTful services. RestTemplate class provides a convenient way to interact with RESTful web services. It supports various HTTP methods (GET, POST, PUT, DELETE, etc.) and allows you to send HTTP requests, handle responses, and perform data binding between Java objects and JSON/XML representations.
// 1. HTTP GET request: getForObject(url, responseType): Sends a GET request to the specified URL and retrieves the response body converted into the specified response type. getForEntity(url, responseType): Sends a GET request to the specified URL and retrieves the complete ResponseEntity including status code, headers, and the response body.
// 2. HTTP POST request: postForObject(url, request, responseType): Sends a POST request to the specified URL with the given request object as the request body, and retrieves the response body converted into the specified response type. postForEntity(url, request, responseType): Sends a POST request to the specified URL with the given request object as the request body, and retrieves the complete ResponseEntity including status code, headers, and the response body.
// 3. HTTP PUT request: put(url, request): Sends a PUT request to the specified URL with the given request object as the request body.
// 4. HTTP DELETE request: delete(url): Sends a DELETE request to the specified URL.
// 5. HTTP OPTIONS request: optionsForAllow(url): Sends an OPTIONS request to the specified URL and retrieves the allowed HTTP methods as a Set<String>.
// 6. HTTP HEAD request: headForHeaders(url): Sends a HEAD request to the specified URL and retrieves the response headers as a HttpHeaders object.