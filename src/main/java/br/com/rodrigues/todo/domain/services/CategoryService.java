package br.com.rodrigues.todo.domain.services;

import br.com.rodrigues.todo.api.dto.category.CategoryRequestDTO;
import br.com.rodrigues.todo.api.dto.category.CategoryResponseDTO;
import br.com.rodrigues.todo.api.dto.utils.PageableDTO;
import br.com.rodrigues.todo.domain.entities.Category;
import br.com.rodrigues.todo.domain.repositories.CategoryRepository;
import br.com.rodrigues.todo.domain.services.map.CategoryMapper;
import br.com.rodrigues.todo.domain.services.map.MapPage;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.BusinessException;
import br.com.rodrigues.todo.infrastructure.exceptions.custom.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;
    private final MapPage mapPage;
    private final UserService userService;

    public CategoryResponseDTO createCategoryBy (String userId, CategoryRequestDTO dto){
        userService.validateUser(userId);
        return categoryMapper.toDto(categoryRepository.save(categoryMapper.toEntity(userId, dto)));
    }

    public CategoryResponseDTO detailsCategoryBy (String userId, String categoryId){
        return categoryMapper.toDto(validateNote(categoryId, userId));
    }

    public PageableDTO listAllCategoriesBy(String userId, Pageable pageable) {
        userService.validateUser(userId);

        Page<Category> page = categoryRepository.findAllCategoryByUserId(userId, pageable);

        var response = categoryMapper.toListDto(page.getContent());

        return mapPage.mapToResponseAll(response, page);
    }

    public CategoryResponseDTO updateCategoryBy(String userId, String categoryId, CategoryRequestDTO dto){
        var response = categoryMapper.updateEntity(validateNote(categoryId, userId), dto);
        categoryRepository.save(response);
        return categoryMapper.toDto(response);
    }

    public void deleteCategoryBy (String userId, String categoryId){
        categoryMapper.toDto(validateNote(categoryId, userId));

        categoryRepository.deleteById(categoryId);
    }

    public Category validateNote(String categoryId, String userId) {

        var user = userService.validateUser(userId);
        var category = categoryRepository.findById(categoryId).orElseThrow(
                () -> new NotFoundException("Category does not exists in user"));

        if (!user.getId().equals(category.getUserId())){
            throw new BusinessException("This category does not correspond to that specific user");
        }

        return category;
    }
}
