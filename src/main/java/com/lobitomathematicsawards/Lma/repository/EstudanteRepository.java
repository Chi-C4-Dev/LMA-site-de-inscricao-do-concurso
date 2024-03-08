package com.lobitomathematicsawards.Lma.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;


import com.lobitomathematicsawards.Lma.entities.Estudante;

public interface EstudanteRepository extends JpaRepository<Estudante, UUID> {

	long count();
	
	
	
	Estudante findByName(String name);
	
	void delete(Estudante doc);
}
