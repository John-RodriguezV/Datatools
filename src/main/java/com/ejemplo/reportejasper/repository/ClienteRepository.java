package com.ejemplo.reportejasper.repository;

import com.ejemplo.reportejasper.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {
}
