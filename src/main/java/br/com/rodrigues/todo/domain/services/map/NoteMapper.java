package br.com.rodrigues.todo.domain.services.map;

import br.com.rodrigues.todo.api.dto.note.NoteRequestDTO;
import br.com.rodrigues.todo.api.dto.note.NoteResponseDTO;
import br.com.rodrigues.todo.domain.entities.Note;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class NoteMapper {

    public Note toEntity(String userId, NoteRequestDTO dto) {
        return new Note(
                dto.content(),
                userId
        );
    }

    public NoteResponseDTO toDto(Note entity) {
        return new NoteResponseDTO(
                entity.getId(),
                entity.getContent(),
                entity.getCreatedAt(),
                entity.getUpdatedAt(),
                entity.getUserId()
        );
    }

    public List<Note> toListEntity(String userId, List<NoteRequestDTO> notes) {
        return notes.stream().map(dto -> toEntity(userId, dto)).toList();
    }

    public List<NoteResponseDTO> toListDto(List<Note> notes) {
        return notes.stream().map(this::toDto).toList();
    }

    public Note updateEntity(Note entity, NoteRequestDTO dto) {

        entity.setContent(dto.content() == null ? entity.getContent() : dto.content());
        entity.setUpdatedAt(LocalDateTime.now());

        return entity;
    }
}
