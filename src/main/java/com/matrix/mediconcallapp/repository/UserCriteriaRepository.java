package com.matrix.mediconcallapp.repository;

import com.matrix.mediconcallapp.entity.Doctor;
import com.matrix.mediconcallapp.entity.User;
import com.matrix.mediconcallapp.enums.UserStatus;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.*;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class UserCriteriaRepository {
    private final EntityManager entityManager;

    public Page<User> findDoctorWithFilter(Pageable pageable,
                                             String name,
                                             String specialty) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<User> query = builder.createQuery(User.class);
        Root<User> root = query.from(User.class);

        Join<User, Doctor> doctorJoin = root.join("doctor");

        List<Predicate> predicates = new ArrayList<>();
        predicates.add(builder.equal(root.get("status"), UserStatus.ACTIVE));

        if (name != null) {
            predicates.add(builder.equal(root.get("name"), name));
        }

        if (name != null) {
            predicates.add(builder.equal(root.get("surname"), name));
        }

        if (specialty != null) {
            String lowerCaseSpecialty = specialty.toLowerCase();
            Path<String> specialtyPath = doctorJoin.get("specialty");

            predicates.add(builder.or(
                    builder.like(builder.lower(specialtyPath), "%" + lowerCaseSpecialty + "%")
            ));
        }

        query.where(predicates.toArray(new Predicate[0]));
        query.orderBy(builder.asc(root.get("id")));

        TypedQuery<User> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult(pageable.getPageNumber() * pageable.getPageSize());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<User> userList = typedQuery.getResultList();

        int userListSize = entityManager.createQuery(query).getResultList().size();
        return new PageImpl<User>(userList, pageable, userListSize);
    }
}

