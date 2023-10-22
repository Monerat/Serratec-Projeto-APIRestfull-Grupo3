package br.com.techtoy.techtoy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.techtoy.techtoy.model.Log;

public interface LogRepository extends JpaRepository<Log, Long> {

}
