package pl.tomo.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import pl.tomo.entity.File;

public interface FileRepository extends JpaRepository<File, Integer>{

}
