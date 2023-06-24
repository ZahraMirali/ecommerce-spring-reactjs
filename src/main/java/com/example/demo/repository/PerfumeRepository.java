package com.example.demo.repository;

import com.example.demo.domain.Perfume;
import com.example.demo.repository.projection.PerfumeProjection;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerfumeRepository extends JpaRepository<Perfume, Long> {

    List<PerfumeProjection> findAllByOrderByIdAsc();

    @Query("SELECT perfume FROM Perfume perfume ORDER BY perfume.id ASC")
    Page<PerfumeProjection> findAllByOrderByIdAsc(Pageable pageable);

    List<Perfume> findByPerfumerOrderByPriceDesc(String perfumer);

    List<Perfume> findByPerfumeGenderOrderByPriceDesc(String perfumeGender);

    List<Perfume> findByIdIn(List<Long> perfumesIds);

    @Query("SELECT perfume FROM Perfume perfume WHERE perfume.id IN :perfumesIds")
    List<PerfumeProjection> getPerfumesByIds(List<Long> perfumesIds);

    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE (coalesce(:perfumers, null) IS NULL OR perfume.perfumer IN :perfumers) " +
            "AND (coalesce(:genders, null) IS NULL OR perfume.perfumeGender IN :genders) " +
            "AND (coalesce(:priceStart, null) IS NULL OR perfume.price BETWEEN :priceStart AND :priceEnd) " +
            "ORDER BY CASE WHEN :sortByPrice = true THEN perfume.price ELSE -perfume.price END ASC")
    Page<PerfumeProjection> findPerfumesByFilterParams(
            List<String> perfumers,
            List<String> genders,
            Integer priceStart,
            Integer priceEnd,
            boolean sortByPrice,
            Pageable pageable);

    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE UPPER(perfume.perfumer) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price DESC")
    Page<PerfumeProjection> findByPerfumer(String text, Pageable pageable);

    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE UPPER(perfume.perfumeTitle) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price DESC")
    Page<PerfumeProjection> findByPerfumeTitle(String text, Pageable pageable);

    @Query("SELECT perfume FROM Perfume perfume " +
            "WHERE UPPER(perfume.country) LIKE UPPER(CONCAT('%',:text,'%')) " +
            "ORDER BY perfume.price DESC")
    Page<PerfumeProjection> findByManufacturerCountry(String text, Pageable pageable);
}


// The sorting behavior is determined by the OrderByIdAsc part of the method name

// The reason for using @Query in this case is to provide a custom sorting behavior for the findAllByOrderByIdAsc() method. By default, when using Spring Data JPA's findAll() method, the results are sorted by the primary key (in this case, the id column) in ascending order. However, using @Query allows you to explicitly define the sorting behavior based on your specific requirements.
// SELECT perfume: Specifies the entity to be selected. In this case, it selects the Perfume entity.
// FROM Perfume perfume: Specifies the entity from which the data is retrieved. In this case, it refers to the Perfume entity.

// The coalesce function is used here to handle the case when :perfumers is null or empty and convert it to null. coalesce is a SQL function that returns the first non-null expression from a list of expressions.

// UPPER(perfume.perfumer): Converts the perfumer attribute to uppercase.
// The purpose of % in this context is to represent any number of characters (including zero characters) in the pattern. It allows for partial matching against the perfumer attribute. The % wildcard characters allow for matching any characters before and after the search text.
// In SQL, the LIKE operator is used to compare a value against a pattern. It allows for pattern matching based on wildcard characters: %: Matches any sequence of characters (including zero characters). _: Matches any single character.

// the END keyword marks the end of the CASE statement

// The NULLS LAST keyword can be added to ensure that any rows with NULL values for perfume.price are placed at the end of the sorted result.