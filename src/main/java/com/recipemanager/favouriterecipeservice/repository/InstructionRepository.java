package com.recipemanager.favouriterecipeservice.repository;

import com.recipemanager.favouriterecipeservice.model.Instruction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InstructionRepository extends JpaRepository<Instruction, String> {
}
