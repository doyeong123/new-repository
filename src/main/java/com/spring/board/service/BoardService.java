package com.spring.board.service;


import com.spring.board.dto.BoardDTO;
import com.spring.board.entity.BoardEntity;
import com.spring.board.repository.BoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

// Service에서 필요한 작업
// DTO를 Entity로 변환하는 작업 (Repository에 넘겨줄 때는 Entity로 넘겨준다.)
// Entity를 DTO로 변환하는 작업. (조회같은 작업을 할 때에는
// Repository에서 Entity를 가져오는데 그것을 DTO로 변환.)


@Service
@RequiredArgsConstructor
public class BoardService {

    private final BoardRepository boardRepository;

    public void save(BoardDTO boardDTO) {

        BoardEntity boardEntity = BoardEntity.toSaveEntity(boardDTO);
        boardRepository.save(boardEntity);

    }

    public List<BoardDTO> findAll() {

        List<BoardEntity> boardEntityList = boardRepository.findAll();
        List<BoardDTO> boardDTOList = new ArrayList<>();
    }

}
