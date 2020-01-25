package com.example.callsservice.Repository;

import com.example.callsservice.Entity.Call;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CallRepository extends JpaRepository<Call, Long> {
}
