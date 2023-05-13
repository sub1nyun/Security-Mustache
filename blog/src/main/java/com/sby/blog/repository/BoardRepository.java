package com.sby.blog.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sby.blog.model.Board;

public interface BoardRepository extends JpaRepository<Board, Integer>{

}
