package com.crud.repository;

import com.crud.entity.Guia;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GuiasRepository extends MongoRepository<Guia, String> {

  List<Guia> findByTitleContaining(String title);
  List<Guia> findByPublished(boolean published);
}