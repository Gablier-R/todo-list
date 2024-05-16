package br.com.rodrigues.todo.domain.services;


import br.com.rodrigues.todo.api.dto.note.NoteRequestDTO;
import br.com.rodrigues.todo.api.dto.note.NoteResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Note;
import br.com.rodrigues.todo.domain.repositories.NoteRepository;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.domain.services.map.NoteMapper;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final NoteMapper noteMapper;
    private final MapPage mapPage;

    private final UserService userService;

    public NoteResponseDTO createNoteBy (String userId, NoteRequestDTO dto){
        var user = userService.validateUser(userId);
        return noteMapper.toDto(noteRepository.save(noteMapper.toEntity(userId, dto)));
    }

    public NoteResponseDTO detailsNoteBy (String userId, String noteId){
        return noteMapper.toDto(validateNote(noteId, userId));
    }

    public PageableDTO listAllNotesBy(String userId, Pageable pageable) {
        userService.validateUser(userId);
        Page<Note> page = noteRepository.findAllNoteByUserId(userId, pageable);

        var response = noteMapper.toListDto(page.getContent());

        return mapPage.mapToResponseAll(response, page);
    }

    public NoteResponseDTO updateNoteBy(String userId, String noteId, NoteRequestDTO dto){
        var response = noteMapper.updateEntity(validateNote(noteId, userId), dto);
        noteRepository.save(response);
        return noteMapper.toDto(response);
    }

    public void deleteNoteBy (String userId, String noteId){

        validateNote(noteId, userId);
        noteRepository.deleteById(noteId);
    }

    public Note validateNote(String noteId, String userId) {

        var user = userService.validateUser(userId);
        var note = noteRepository.findById(noteId).orElseThrow(
                () -> new NotFoundException("Note does not exists in user"));

        if (!user.getId().equals(note.getUserId())){
            throw new BusinessException("This note does not correspond to that specific user");
        }

        return note;
    }

}
