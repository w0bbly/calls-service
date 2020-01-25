package com.example.callsservice.Repository;

import com.example.callsservice.Entity.Call;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface CallRepository extends JpaRepository<Call, Long>, PagingAndSortingRepository<Call, Long> {
    Page<Call> findAllByType(Call.Type type, Pageable pageable);
}
