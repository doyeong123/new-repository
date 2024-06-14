package com.spring.board.controller;

import com.spring.board.dto.BoardDTO;
import com.spring.board.service.BoardService;
import lombok.RequiredArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor //Service랑 엮기
//공통 매핑
@RequestMapping("/board")
public class BoardController {

    private final BoardService boardService;

    @GetMapping("/save")
    public String saveForm() {
        return "save";
    }

    @PostMapping("/save")
    public String save(@ModelAttribute BoardDTO boardDTO) {
        System.out.println("boardDTO = " + boardDTO);
        boardService.save(boardDTO);
        return "index";
    }

    @GetMapping("/")
    public String findAll(Model model) {
        //DB에서 전체 게시글 데이터를 가져와서 list.html에 보여준다.
        List<BoardDTO> boardDTOList = boardService.findAll();
        model.addAttribute("boardList", boardDTOList);
        return "list";
    }

    @GetMapping("/{id}")
    public String findById(@PathVariable Long id, Model model,
                           @PageableDefault Pageable pageable) {
//        해당 게시글의 조회수를 하나 올리고,
//        게시글의 데이터를 가져와서 detail.html에 출력
        boardService.updateHits(id);
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("board", boardDTO);
        model.addAttribute("page", pageable.getPageNumber());

        return "detail";

    }


    @GetMapping("/update/{id}")
    public String updateForm(@PathVariable Long id, Model model) {
        BoardDTO boardDTO = boardService.findById(id);
        model.addAttribute("boardUpdate", boardDTO);
        return "update";
    }

    @PostMapping("/update")
    public String update(@ModelAttribute BoardDTO boardDTO, Model model) {
        BoardDTO board = boardService.update(boardDTO);
        model.addAttribute("board", board);
        return "detail";
//        return "redirect:/board/" + board.getId();
    }

    @GetMapping("/delete/{id}")
    public String delete(@PathVariable Long id) {

        boardService.deleteById(id);
        return "redirect:/board/";
    }

   /* //paging 처리
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
        // 이 메서드는 "/paging" URL로 GET 요청이 왔을 때 실행됩니다.
        // @PageableDefault(page = 1) 어노테이션을 사용하여 기본 페이지 번호를 1로 설정합니다.
        // Pageable 객체는 페이징 정보(페이지 번호, 페이지 크기 등)를 담고 있습니다.
        // Model 객체는 뷰에 전달할 데이터를 담는 역할을 합니다.

        Page<BoardDTO> boardList = (Page<BoardDTO>) boardService.paging(pageable);
        // boardService.paging() 메서드를 호출하여 페이징 처리된 게시물 목록을 가져옵니다.
        // Page<BoardDTO> 객체에는 게시물 목록과 페이징 정보가 포함되어 있습니다.

        int blockLimit = 3;
        // 한 번에 보여줄 페이지 번호 개수를 3개로 설정합니다.

        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1;
        // 현재 페이지 번호를 blockLimit으로 나누고 올림 처리하여 시작 페이지 번호를 계산합니다.
        // 예를 들어, 현재 페이지가 7이고 blockLimit이 3이면, 시작 페이지 번호는 7 / 3 = 2.33 => 3 => (3-1)*3+1 = 7

        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();
        // 끝 페이지 번호를 계산합니다.
        // 시작 페이지 번호 + blockLimit - 1 이 총 페이지 수보다 작으면 그 값을 사용하고,
        // 그렇지 않으면 총 페이지 수를 사용합니다.

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        // 계산한 페이징 정보를 Model 객체에 담아 뷰에 전달합니다.

        return "paging";
        // "paging" 뷰로 렌더링합니다.
    }*/

    // /board/paging?page=1
    @GetMapping("/paging")
    public String paging(@PageableDefault(page = 1) Pageable pageable, Model model) {
//        pageable.getPageNumber();
        Page<BoardDTO> boardList = boardService.paging(pageable);
        int blockLimit = 3;
        int startPage = (((int)(Math.ceil((double)pageable.getPageNumber() / blockLimit))) - 1) * blockLimit + 1; // 1 4 7 10 ~~
        int endPage = ((startPage + blockLimit - 1) < boardList.getTotalPages()) ? startPage + blockLimit - 1 : boardList.getTotalPages();

        // page 갯수 20개
        // 현재 사용자가 3페이지
        // 1 2 3
        // 현재 사용자가 7페이지
        // 7 8 9
        // 보여지는 페이지 갯수 3개
        // 총 페이지 갯수 8개

        model.addAttribute("boardList", boardList);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);
        return "paging";

    }
}
