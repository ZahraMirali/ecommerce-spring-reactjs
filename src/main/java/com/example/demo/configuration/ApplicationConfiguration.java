package com.example.demo.configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.projection.SpelAwareProxyProjectionFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class ApplicationConfiguration {

    @Value("${amazon.aws.access-key}")
    private String awsAccessKey;

    @Value("${amazon.aws.secret-key}")
    private String awsAccessSecret;

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new BCryptPasswordEncoder(8);
    }

    @Bean
    public AmazonS3 s3Client() {
        AWSCredentials credentials = new BasicAWSCredentials(awsAccessKey, awsAccessSecret);
        return AmazonS3ClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(credentials))
                .withRegion(Regions.EU_CENTRAL_1)
                .build();
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        return mapper;
    }

    @Bean
    public SpelAwareProxyProjectionFactory projectionFactory() { // ???
        return new SpelAwareProxyProjectionFactory();
    }
}

// org.modelmapper.ModelMapper is a Java library that provides a convenient way to map data between different object models. It simplifies the process of copying data from one object to another by automatically mapping properties based on their names and types.
// 1. STRICT: exact same names and types in both source and destination objects
// 2. LOOSE: The matching process ignores case differences and underscores between words in property names. For example, firstName and first_name would be considered a match. Types of properties still need to match for successful mapping.
// 3. STANDARD: it ignores case differences and underscores between words in property names. Additionally, it allows for type conversion between compatible types, such as mapping an int property to a long property.
// 4. ACCESSOR_RESOLVER: Matches properties based on the getter and setter method names. The property names are derived from the method names using JavaBean naming conventions. example: DestinationObject destination = mapper.map(source, DestinationObject.class); destination.setFullName(source.getFirstName() + " " + source.getLastName());