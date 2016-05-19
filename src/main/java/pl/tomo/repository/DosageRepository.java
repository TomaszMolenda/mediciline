package pl.tomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.tomo.entity.Dosage;

public interface DosageRepository extends JpaRepository<Dosage, Integer>{

}
