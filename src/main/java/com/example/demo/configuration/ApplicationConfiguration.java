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
    public SpelAwareProxyProjectionFactory projectionFactory() {
        return new SpelAwareProxyProjectionFactory();
    }
}

// org.modelmapper.ModelMapper is a Java library that provides a convenient way to map data between different object models. It simplifies the process of copying data from one object to another by automatically mapping properties based on their names and types.
// 1. STRICT: exact same names and types in both source and destination objects
// 2. LOOSE: The matching process ignores case differences and underscores between words in property names. For example, firstName and first_name would be considered a match. Types of properties still need to match for successful mapping.
// 3. STANDARD: it ignores case differences and underscores between words in property names. Additionally, it allows for type conversion between compatible types, such as mapping an int property to a long property.
// 4. ACCESSOR_RESOLVER: Matches properties based on the getter and setter method names. The property names are derived from the method names using JavaBean naming conventions. example: DestinationObject destination = mapper.map(source, DestinationObject.class); destination.setFullName(source.getFirstName() + " " + source.getLastName());

// The BCryptPasswordEncoder class is a part of the Spring Security framework and provides password encoding and verification using the bcrypt hashing algorithm. The strength factor indicates the computational cost of the hashing algorithm, with a higher value resulting in slower but more secure hashing.

// 1. BasicAWSCredentials: This implementation represents basic AWS access credentials using an access key ID and secret access key. It is created with the
// 2. EnvironmentVariableCredentialsProvider: This class retrieves AWS credentials from environment variables, such as AWS_ACCESS_KEY_ID and AWS_SECRET_ACCESS_KEY. It can be useful in certain deployment scenarios where environment variables are used to securely store credentials.
// 3. SystemPropertiesCredentialsProvider: This class retrieves AWS credentials from system properties, such as aws.accessKeyId and aws.secretKey. It allows you to provide the credentials through JVM system properties.
// 4. ProfileCredentialsProvider: This class retrieves AWS credentials from a profile in the AWS configuration file (usually located at ~/.aws/credentials). It allows you to store multiple profiles with different sets of credentials and select the appropriate profile based on the context.
// 5. InstanceProfileCredentialsProvider: This class retrieves AWS credentials from an EC2 instance's IAM role, allowing applications running on EC2 instances to automatically inherit the instance's role-based credentials.
// 6. AnonymousAWSCredentials: This implementation represents anonymous AWS access credentials, which are used when accessing certain AWS services without providing explicit credentials. It does not require any parameters for instantiation.
// 7. DefaultAWSCredentialsProviderChain: This class is used to automatically fetch AWS credentials from various sources, such as environment variables, system properties, and AWS configuration files. It provides a flexible way to manage credentials without hardcoding them in your code.

// AmazonS3ClientBuilder: This is a builder class provided by the AWS SDK for Java specifically for creating an instance of the Amazon S3 client. It allows you to configure various properties and settings before creating the client.
// standard(): This is a static method of the AmazonS3ClientBuilder class. It returns an instance of the builder with default configuration settings.

// AWSCredentialsProvider: This is an interface in the AWS SDK for Java that defines a way to provide AWS access credentials. It has methods such as getCredentials() that return the AWS access credentials.
// AWSStaticCredentialsProvider: This class is an implementation of the AWSCredentialsProvider interface that takes static AWS access credentials as input. It is used to provide AWS access credentials when creating AWS clients, such as the Amazon S3 client or the Amazon DynamoDB client.

// withRegion() method allows you to set the AWS region for the AmazonS3 client. The AWS region is identified by a unique region identifier, such as "us-west-2" for the US West (Oregon) region or "eu-central-1" for the EU (Frankfurt) region. The withRegion() method expects you to pass the desired region as a parameter. The AWS region is a geographic area where AWS services, such as Amazon S3, are deployed and available.

// SpelAwareProxyProjectionFactory is a class provided by the Spring Data JPA framework that allows you to create dynamic projections for your entities. Projections in Spring Data JPA enable you to fetch only specific fields or properties of an entity instead of retrieving the entire entity. This can help optimize performance by reducing the amount of data transferred from the database. This factory can be used to create projections dynamically using SpEL (Spring Expression Language) expressions. Projections are interfaces that define the subset of properties or fields that you want to retrieve from an entity.
// The term "SpelAware" indicates that the factory is aware of and capable of handling SpEL expressions. It means that the SpelAwareProxyProjectionFactory understands and supports the SpEL syntax and functionality when creating dynamic projections for entities.
// Here are some key features and examples of SpEL expressions: 1. Property Access: Accessing object properties or fields using the dot notation: person.name 2. Method Invocation: Invoking methods on objects: person.getName() 3. Arithmetic Operations: Performing arithmetic calculations: age + 5 4. Relational Operators: Comparing values using relational operators: salary > 10000 5. Logical Operators: Combining conditions using logical operators: isStudent && isEnrolled 6. Ternary Operator: Evaluating conditional expressions: isAdult ? "Yes" : "No" 7. Collection Projection: Working with collections and performing operations like filtering and mapping: employees.?[salary > 50000] 8. Object Construction: Creating new objects using constructors: new com.example.Person("John", 25) 9. Null Safety: Handling null values in expressions: person?.address?.city
// 1. createProjection: Creates a projection object based on the provided projection interface and SpEL expressions. This method returns an instance of the projection interface that can be used to access the projected properties.
// 2. getProjectionFactory: Returns the underlying DefaultProjectionFactory used by the SpelAwareProxyProjectionFactory. This allows you to access additional methods and customize the projection factory if needed.
// 3. setBeanFactory: Sets the BeanFactory to be used by the projection factory for resolving dependencies and managing beans during projection creation.
// 4. setConversionService: Sets the ConversionService to be used for converting property values during projection creation.
// 5. setSpelExpressionParser: Sets the SpelExpressionParser to be used for parsing SpEL expressions during projection creation.
// 6. setEvaluationContextProvider: Sets the EvaluationContextProvider to be used for providing evaluation contexts for SpEL expressions during projection creation.
// interface CustomerProjection { String getName();String getEmail(); } entities.stream().map(entity -> projectionFactory.createProjection(CustomProjection.class, entity))