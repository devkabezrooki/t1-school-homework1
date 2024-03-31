package com.example.t1.aspect.repository;

import com.example.t1.aspect.model.MethodExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodExecutionRepository extends JpaRepository<MethodExecution, Long> {
}
