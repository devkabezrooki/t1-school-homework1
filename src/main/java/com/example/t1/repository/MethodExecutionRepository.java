package com.example.t1.repository;

import com.example.t1.model.MethodExecution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MethodExecutionRepository extends JpaRepository<MethodExecution, Long> {
}
