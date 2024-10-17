package org.mrshoffen.repository;

import org.mrshoffen.entity.BaseEntity;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

public interface Repository <K extends Serializable, E extends BaseEntity<K>> {


    List<E> findAll();

    Optional<E> findById(K id);
}
