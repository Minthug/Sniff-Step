package SniffStep.repository;

import SniffStep.entity.Board;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByTitle(String title);

    @Query(value = "SELECT b from Board b JOIN FETCH b.member m",
    countQuery = "select count(b) from Board b")
    Page<Board> findAll(Pageable pageable);


    @Query(value = "select b from Board b join fetch b.member m where b.title like '%:title%'",
    countQuery = "select count(b) from Board b where b.title like '%:title%'")
    Page<Board> findAllByTitleContaining(@Param(value = "title") String keyword, Pageable pageable);

    @Query("SELECT b FROM Board b JOIN FETCH b.member JOIN FETCH b.images WHERE b.id = :id")
    Optional<Board> findByIdWithMemberAAndImages(@Param("id") Long id);
}
