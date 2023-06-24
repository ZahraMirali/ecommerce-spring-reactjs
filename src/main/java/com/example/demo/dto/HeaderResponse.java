package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Data
@AllArgsConstructor
public class HeaderResponse<T> {
    private List<T> items;
    private HttpHeaders headers;
}


// Lombok is a Java library that aims to reduce boilerplate code in Java classes by providing annotations that automatically generate common methods and functionality during the compilation process.
// @Getter, @Setter, @ToString
// @NoArgsConstructor, @AllArgsConstructor, @RequiredArgsConstructor: generates a constructor with parameters for the fields marked with @NonNull like: @NonNull private String requiredField;
// @EqualsAndHashCode
// @Data = @Getter, @Setter, @EqualsAndHashCode, and @ToString
// @Builder: @Builder public class Person {fields}; Person person = Person.builder().name("John Doe").age(25).build(); @Builder annotation is equivalent to the manual code example provided below:

// public class Person {
//     private String name;
//
//     public static class Builder {
//         private String name;
//         private int age;
//
//         public Builder name(String name) {
//             this.name = name;
//             return this;
//         }
//
//         public Builder age(int age) {
//             this.age = age;
//             return this;
//         }
//
//         public Person build() {
//             Person person = new Person();
//             person.name = this.name;
//             return person;
//         }
//     }
//
//     public static Builder builder() {
//         return new Builder();
//     }
// }
