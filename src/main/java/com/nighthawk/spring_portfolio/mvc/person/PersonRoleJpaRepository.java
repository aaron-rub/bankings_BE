package com.nighthawk.spring_portfolio.mvc.person;

import org.springframework.data.jpa.repository.JpaRepository;

public interface  PersonRoleJpaRepository extends JpaRepository<PersonRole, Long> {
    @Query(
            value = "SELECT * FROM Person_roles p WHERE p.person_id = :name",
            nativeQuery = true)
    PersonRole findByName(String name);
}
