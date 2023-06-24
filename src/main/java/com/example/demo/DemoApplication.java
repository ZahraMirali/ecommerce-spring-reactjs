package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}


// The schema.graphql file serves as a contract between the server and the clients of your GraphQL API. Clients can query the available fields, specify input parameters, and receive the requested data based on the defined schema. The schema acts as a central point of reference for understanding the structure and capabilities of your GraphQL API.

// V1__Init_BD.sql:
// Sequences are commonly used to generate unique identifiers for primary keys in database tables. By defining a sequence, you can ensure that each row inserted into a table will have a distinct identifier. Annotate the primary key field with @GeneratedValue and specify the generator using @SequenceGenerator
// (order_items_id): This is the column or set of columns to which the unique constraint applies. In this case, the order_items_id column is being constrained to have unique values.
// The constraint ensures that the values in the perfume_id column must exist as primary key values in the referenced perfume table.
// 1. Timestamp-based naming: Prefixing the migration scripts with a timestamp in the format YYYYMMDDHHMMSS ensures that they are executed in the order they were created. 20210615120000_create_users_table.sql
// 2. Version-based naming: If you prefer to use a version number instead of a timestamp, you can assign a unique identifier to each migration script. V1__create_users_table.sql
// 3. Sequential numbering: You can simply number the migration scripts in a sequential manner. 001_create_users_table.sql