package br.com.rodrigues.todo.api.controller;


import br.com.rodrigues.todo.api.dto.category.CategoryRequestDTO;
import br.com.rodrigues.todo.api.dto.category.CategoryResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.CategoryService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

import static br.com.rodrigues.todo.utils.Constants.*;

@RequiredArgsConstructor

@RestController
@RequestMapping("/category")
@Tag(name = "note")
public class CategoryController {

    private final CategoryService categoryService;

    @PostMapping
    ResponseEntity<CategoryResponseDTO> save(JwtAuthenticationToken token, @RequestBody @Valid CategoryRequestDTO dto) {
        var categorySaved = categoryService.createCategoryBy(token.getName(), dto);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(token.getName()+"/"+categorySaved.id())
                .toUri();
        return ResponseEntity.created(location).body(categorySaved);
    }

    @GetMapping
    ResponseEntity<PageableDTO> listAllByUserId(JwtAuthenticationToken token,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                  @RequestParam(defaultValue = DEFAULT_SORT, required = false) String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return new ResponseEntity<>(categoryService.listAllCategoriesBy(token.getName(), pageable), HttpStatus.OK);
    }

    @GetMapping("/{categoryId}")
    ResponseEntity<CategoryResponseDTO> details(JwtAuthenticationToken token, @PathVariable String categoryId) {
        return new ResponseEntity<>(categoryService.detailsCategoryBy(token.getName(), categoryId), HttpStatus.OK);
    }

    @PutMapping("/{categoryId}")
    ResponseEntity<CategoryResponseDTO> update(JwtAuthenticationToken token, @PathVariable String categoryId, @RequestBody CategoryRequestDTO dto) {
        return new ResponseEntity<>(categoryService.updateCategoryBy(token.getName(), categoryId, dto), HttpStatus.OK);
    }

    @DeleteMapping("/{categoryId}")
    ResponseEntity<Void> delete(JwtAuthenticationToken token, @PathVariable String categoryId) {
        categoryService.deleteCategoryBy(token.getName(), categoryId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
