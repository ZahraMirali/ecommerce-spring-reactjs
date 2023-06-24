package com.example.demo.repository.projection;

import org.springframework.beans.factory.annotation.Value;

public interface PerfumeProjection {
    Long getId();
    String getPerfumeTitle();
    String getPerfumer();
    Integer getPrice();
    String getFilename();
    Double getPerfumeRating();
    
    @Value("#{target.reviews.size()}")
    Integer getReviewsCount();

    void setPerfumer(String perfumer);
    void setPerfumeGender(String perfumeGender);
    void setPrice(Integer price);
}


// The target refers to the target entity object being projected.

// Here are a few reasons why you might use projections in JPA repositories:
//1. Reducing Data Transfer: Projections allow you to retrieve only the required attributes of an entity or a combination of attributes from multiple entities. This helps minimize the amount of data transferred between the database and the application, improving performance and reducing network overhead.
//2. Custom Result Structures: Projections provide flexibility in defining custom result structures that are different from the entity itself. You can create DTOs (Data Transfer Objects) or custom projection interfaces/classes to shape the query results according to your specific needs. This allows you to have different views of the data tailored for specific use cases.
//3. Avoiding Entity Associations: Projections can be useful when you want to fetch data without loading associated entities. By selecting specific attributes or joining only required entities, you can avoid unnecessary database joins and reduce the complexity of the resulting query.
//4. Aggregating Data: Projections allow you to aggregate data and compute derived properties using functions or expressions in the query. You can perform calculations, apply transformations, or aggregate values from related entities, providing a summarized view of the data.
//5. Improved Performance: By fetching only the required attributes or using optimized queries, projections can significantly improve performance, especially when dealing with large datasets or complex object graphs.