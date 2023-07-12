package com.example.demo.exception;

import lombok.Getter;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.Map;
import java.util.stream.Collectors;

@Getter
public class InputFieldException extends RuntimeException {

    private final BindingResult bindingResult;
    private final Map<String, String> errorsMap;

    public InputFieldException(BindingResult bindingResult) {
        this.bindingResult = bindingResult;
        this.errorsMap = bindingResult.getFieldErrors().stream()
                .collect(Collectors.toMap(
                        fieldError -> fieldError.getField() + "Error",
                        FieldError::getDefaultMessage
                ));
    }
}

// Collectors.toMap(): List<Person> persons = Arrays.asList(new Person(1, "John"),new Person(2, "Jane"),new Person(3, "Alice")); Map<Integer, String> idNameMap = persons.stream().collect(Collectors.toMap(Person::getId, Person::getName)); => {1=John, 2=Jane, 3=Alice}
// persons.stream() creates a stream of elements from the persons list, allowing us to perform various operations on the elements of the list using the Stream API.
// The collect(...) operation is a terminal operation that collects the elements of the stream into a collection or a single value. In this specific example, collect(Collectors.toMap(...)) collects the elements of the stream into a Map using the toMap(...) collector

// Collectors
// 1. toList(): Collects the stream elements into a List.
// 2. toSet(): Collects the stream elements into a Set, removing duplicates.
// 3. toCollection(collectionFactory): Collects the stream elements into a specific collection type created using the provided collection factory.
// 4. toMap(keyMapper, valueMapper): Collects the stream elements into a Map, using the provided key and value mappers to determine the keys and values of the map entries.
// 5. groupingBy(classifier): Collects the stream elements into a Map where the keys are determined by the provided classifier function, and the values are the elements that share the same key.
// 6. joining(delimiter): Collects the stream elements into a single String, joining them with the provided delimiter.

// stream:
// Stream operations: Streams offer a rich set of operations like filtering, mapping, sorting, and reducing, which allow you to perform complex data manipulations and transformations with ease.
// Functional programming: Streams enable a functional programming style where you can chain multiple operations together, making your code more concise and expressive.
// Lazy evaluation: Streams are evaluated on-demand, meaning that the elements are processed only when needed. This can lead to improved performance and efficiency, especially when working with large data sets.
// Parallel processing: Streams provide built-in support for parallel execution, allowing you to take advantage of multi-core processors and speed up certain operations by dividing the work among multiple threads.