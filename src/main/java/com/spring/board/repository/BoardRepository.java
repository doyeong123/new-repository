package com.spring.board.repository;

import com.spring.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BoardRepository extends JpaRepository<BoardEntity, Long> {

    // update board_table set board_hits = board_hits+1 where id=?
    @Modifying //Modifying 어노테이션은 update,delete 사용할 땐 필수적으로 사용.
    @Query(value = "update BoardEntity b set b.boardHits=b.boardHits+1 where b.id =:id")
    void updateHits(@Param("id") Long id);
}
