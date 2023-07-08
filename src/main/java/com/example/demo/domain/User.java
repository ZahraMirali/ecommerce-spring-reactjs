package com.example.demo.domain;

import com.example.demo.enums.AuthProvider;
import com.example.demo.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_id_seq")
    @SequenceGenerator(name = "users_id_seq", sequenceName = "users_id_seq", initialValue = 4, allocationSize = 1)
    private Long id;

    private String email;

    private String password;

    private String firstName;

    private String lastName;

    private String city;

    private String address;

    private String phoneNumber;

    private String postIndex;

    private String activationCode;

    private String passwordResetCode;

    private boolean active;

    @Enumerated(EnumType.STRING)
    private AuthProvider provider;

    @ElementCollection(targetClass = Role.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Enumerated(EnumType.STRING)
    private Set<Role> roles;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}


// The allocationSize attribute of @SequenceGenerator specifies the increment size for the sequence
// The sequenceName attribute of @SequenceGenerator specifies the name of the database sequence to use for generating the identifier values. This should match the name of the sequence defined in the database schema.
// The name attribute of @SequenceGenerator is used to specify a unique name for the generator itself.

// @Enumerated is used to map an enum type attribute to a database column. It specifies how the enum values should be persisted and retrieved from the database.
// EnumType.ORDINAL: This is the default behavior. It maps the enum values to their corresponding ordinal positions (indexes) in the enum declaration. The ordinal values are stored in the database column. However, it's important to note that relying on ordinal values can be problematic if the enum declaration order changes in future versions of the code.
// EnumType.STRING: This mapping option stores the string representation of the enum values in the database column. The string values are obtained by calling the name() method on the enum instances.

// @ElementCollection: This annotation indicates that the roles field in the User entity represents a collection of elements.
// targetClass specifies the class of elements in the collection
// With EAGER fetching, the collection will be loaded immediately along with the User entity. Alternatively, you can use LAZY fetching to load the collection on-demand when it is accessed.
// @CollectionTable specifies the details of the collection table that will be created in the database to store the elements of the collection. The name attribute defines the name of the table and the joinColumns attribute specifies the join column that links the collection table to the owning entity table

// @Entity is used to mark a class as an entity, representing a persistent object that is mapped to a database table. and @Table is used to define the details of the corresponding database table.
// @Entity(name = "users"):
// Scope: The name specified in @Entity is primarily used to provide an alternate name for the entity itself. It affects how the entity is referred to within the persistence context, entity queries, and other entity-related operations.
// Impact: The name in @Entity does not directly affect the name of the corresponding database table. By default, if no @Table annotation is specified, the table name will be derived from the entity's class name.
// @Table(name = "users"):
// Scope: The name specified in @Table is used to define the name of the corresponding database table that the entity is mapped to.
// Impact: The name in @Table directly affects the name of the database table. It determines how the entity's attributes are mapped to table columns and how the table is referenced in SQL queries.