package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static br.com.rodrigues.todo.utils.Constants.DEFAULT_PAGE_NUMBER;
import static br.com.rodrigues.todo.utils.Constants.DEFAULT_PAGE_SIZE;

@RequiredArgsConstructor

@RestController
@RequestMapping("/all")
public class GeneralController {

    private final UserService userService;

    @GetMapping
    ResponseEntity<PageableDTO> All(@RequestParam ( defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                    @RequestParam ( defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize){
        Pageable pageable = PageRequest.of(pageNo, pageSize);
        return new ResponseEntity<>(userService.listAll(pageable), HttpStatus.OK);
    }
}
