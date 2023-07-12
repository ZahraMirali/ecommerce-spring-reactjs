package com.example.demo.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.Set;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CaptchaResponse {

    private boolean success;

    @JsonAlias("error-codes")
    private Set<String> errorCodes;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Set<String> getErrorCodes() {
        return errorCodes;
    }

    public void setErrorCodes(Set<String> errorCodes) {
        this.errorCodes = errorCodes;
    }
}


// @JsonIgnoreProperties annotation is used in Jackson (JSON library) to ignore any unknown properties during the deserialization process.
// The deserialization process refers to converting JSON data into an instance of the class. When you deserialize JSON into a Java object using a library like Jackson, the library reads the JSON data and maps it to the fields of the Java class.

// @JsonIgnoreProperties(...):
// 1. value: Specifies one or more property names to ignore. You can provide a single property name as a string or an array of property names.
// 2. ignoreUnknown: Indicates whether to ignore unknown properties during deserialization. If set to true, any properties in the JSON that do not have corresponding fields in the Java class will be ignored.
// 3. allowGetters: Specifies whether to ignore properties that only have getter methods. If set to true, properties that have only getter methods will be ignored during serialization and deserialization.
// 4. allowSetters: Specifies whether to ignore properties that only have setter methods. If set to true, properties that have only setter methods will be ignored during serialization and deserialization.

// @JsonAlias annotation is used in Jackson to specify alternative names for a property during deserialization. It allows you to map multiple names from the JSON to a single field in the Java class. @JsonAlias({"firstName", "first_name"}) private String name; If the JSON contains a property named "name", it will be mapped to the name field in the MyModel class. However, if the JSON contains properties named "firstName" or "first_name", they will also be mapped to the name field.