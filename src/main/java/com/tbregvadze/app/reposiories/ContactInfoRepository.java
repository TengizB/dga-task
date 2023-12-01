package com.tbregvadze.app.reposiories;

import com.tbregvadze.app.entities.ContactInfo;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ContactInfoRepository extends JpaRepository<ContactInfo, UUID> {
    Optional<ContactInfo> findById(UUID id);
    List<ContactInfo> findByUserId(UUID userId);
    Optional<ContactInfo> findByUserIdAndValue(UUID userId, String value);

    Page<ContactInfo> findAll(Specification<ContactInfo> specifications, Pageable pageable);

    default Page<ContactInfo> searchContacts(String query, Pageable pageable) {
        return findAll(getSearchSpecification(query), pageable);
    }

    default Specification<ContactInfo> getSearchSpecification(String query) {
        return (root, queryBuilder, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (query != null && !query.isEmpty()) {
                String likeQuery = "%" + query.toLowerCase() + "%";
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("firstname")), likeQuery),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("user").get("lastname")), likeQuery),
                        criteriaBuilder.like(criteriaBuilder.lower(root.get("value")), likeQuery)
                ));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}
