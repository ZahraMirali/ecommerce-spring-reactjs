package com.example.demo.service.graphql;

import com.example.demo.service.OrderService;
import com.example.demo.service.PerfumeService;
import graphql.GraphQL;
import graphql.schema.GraphQLSchema;
import graphql.schema.idl.RuntimeWiring;
import graphql.schema.idl.SchemaGenerator;
import graphql.schema.idl.SchemaParser;
import graphql.schema.idl.TypeDefinitionRegistry;
import jakarta.annotation.PostConstruct;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;

@Component
@RequiredArgsConstructor
public class GraphQLProvider {

    private final PerfumeService perfumeService;

    private final OrderService orderService;

    @Value("classpath:graphql/schemas.graphql")
    private Resource resource;

    @Getter
    private GraphQL graphQL;

    @PostConstruct
    public void loadSchema() throws IOException {
        File fileSchema = resource.getFile();
        TypeDefinitionRegistry typeRegistry = new SchemaParser().parse(fileSchema);
        RuntimeWiring wiring = buildRuntimeWiring();
        GraphQLSchema schema = new SchemaGenerator().makeExecutableSchema(typeRegistry, wiring);
        graphQL = GraphQL.newGraphQL(schema).build();
    }

    private RuntimeWiring buildRuntimeWiring() {
        return RuntimeWiring.newRuntimeWiring()
                .type("Query", typeWiring -> typeWiring
                        .dataFetcher("perfumes", perfumeService.getAllPerfumesByQuery())
                        .dataFetcher("perfumesIds", perfumeService.getAllPerfumesByIdsQuery())
                        .dataFetcher("perfume", perfumeService.getPerfumeByQuery())
                        .dataFetcher("orders", orderService.getAllOrdersByQuery())
                        .dataFetcher("ordersByEmail", orderService.getUserOrdersByEmailQuery()))
                .build();
    }
}

// PostConstruct is used to annotate a method that should be executed after the dependency injection and initialization of a bean.

// The RuntimeWiring class allows you to define how the fields in your GraphQL schema should be resolved by providing data fetchers for each field. It acts as a bridge between the GraphQL schema and the actual data-fetching logic.
