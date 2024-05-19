package br.com.rodrigues.todo.api.controller;

import br.com.rodrigues.todo.api.dto.note.NoteRequestDTO;
import br.com.rodrigues.todo.api.dto.note.NoteResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.services.NoteService;
import io.swagger.v3.oas.annotations.Operation;
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
@RequestMapping("/note")
@Tag(name = "note")
public class NoteController {

    private final NoteService noteService;

    @Operation(summary = "Create new Note", method ="POST")
    @PostMapping
    ResponseEntity<NoteResponseDTO> save(JwtAuthenticationToken token, @RequestBody @Valid NoteRequestDTO note) {
        var savedNote = noteService.createNoteBy(token.getName(), note);

        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(token.getName()+"/"+savedNote.id())
                .toUri();
        return ResponseEntity.created(location).body(savedNote);
    }

    @Operation(summary = "List all Notes", method ="GET")
    @GetMapping
    ResponseEntity<PageableDTO> allByUser(JwtAuthenticationToken token,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_NUMBER, required = false) int pageNo,
                                                  @RequestParam(defaultValue = DEFAULT_PAGE_SIZE, required = false) int pageSize,
                                                  @RequestParam(defaultValue = DEFAULT_SORT, required = false) String sortBy) {
        Pageable pageable = PageRequest.of(pageNo, pageSize, Sort.by(sortBy));
        return new ResponseEntity<>(noteService.listAllNotesBy(token.getName(), pageable), HttpStatus.OK);
    }

    @Operation(summary = "Details Note", method ="GET")
    @GetMapping("/{noteId}")
    ResponseEntity<NoteResponseDTO> details(JwtAuthenticationToken token, @PathVariable String noteId) {
        return new ResponseEntity<>(noteService.detailsNoteBy(token.getName(), noteId), HttpStatus.OK);
    }

    @Operation(summary = "Update Note", method ="PUT")
    @PutMapping("/{noteId}")
    ResponseEntity<NoteResponseDTO> update(JwtAuthenticationToken token, @PathVariable String noteId, @RequestBody NoteRequestDTO dto) {
        return new ResponseEntity<>(noteService.updateNoteBy(token.getName(), noteId, dto), HttpStatus.OK);
    }

    @Operation(summary = "Delete Note", method ="DELETE")
    @DeleteMapping("/{noteId}")
    ResponseEntity<Void> delete(JwtAuthenticationToken token, @PathVariable String noteId) {
        noteService.deleteNoteBy(token.getName(), noteId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
