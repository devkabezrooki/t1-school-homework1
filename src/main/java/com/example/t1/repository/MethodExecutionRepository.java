package com.example.t1.repository;

import com.example.t1.model.MethodExecution;
import com.example.t1.model.enums.MethodType;
import com.example.t1.model.dto.MethodAverageExecutionTimeDto;
import com.example.t1.model.dto.MethodExecutionCountDto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MethodExecutionRepository extends JpaRepository<MethodExecution, Long> {

    String AVG_QUERY_BEGINNING = "SELECT new com.example.t1.model.dto.MethodAverageExecutionTimeDto(m.methodType, AVG(m.executionTime)) " +
            "FROM MethodExecution m";

    @Query(AVG_QUERY_BEGINNING + " GROUP BY m.methodType")
    List<MethodAverageExecutionTimeDto> getMethodsAverageExecutionTime();

    @Query(AVG_QUERY_BEGINNING + " WHERE m.isAsync = ?1 GROUP BY m.methodType")
    List<MethodAverageExecutionTimeDto> getMethodsAverageExecutionTimeByAsync(boolean isAsync);

    @Query(AVG_QUERY_BEGINNING + " WHERE m.methodType = ?1 GROUP BY m.methodType")
    MethodAverageExecutionTimeDto getMethodAverageExecutionTimeByMethodType(MethodType methodType);

    @Query("SELECT new com.example.t1.model.dto.MethodAverageExecutionTimeDto(AVG(m.executionTime)) " +
            "FROM MethodExecution m")
    MethodAverageExecutionTimeDto getAllMethodsAverageExecutionTime();

    @Query("SELECT new com.example.t1.model.dto.MethodAverageExecutionTimeDto(AVG(m.executionTime)) " +
            "FROM MethodExecution m WHERE m.isAsync = ?1")
    MethodAverageExecutionTimeDto getAllMethodsAverageExecutionTimeByAsync(boolean isAsync);

    @Query("SELECT new com.example.t1.model.dto.MethodExecutionCountDto(COUNT(m)) FROM MethodExecution m")
    MethodExecutionCountDto getMethodExecutionsCount();

    @Query("SELECT new com.example.t1.model.dto.MethodExecutionCountDto(m.methodType, COUNT(m)) FROM MethodExecution m " +
            "WHERE m.methodType = ?1 GROUP BY m.methodType")
    MethodExecutionCountDto getMethodExecutionsCountByMethodType(MethodType methodType);
}
