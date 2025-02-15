package com.microservice.backend_scrapify.logic.category.service;

import com.microservice.backend_scrapify.commons.SequenceGeneratorService;
import com.microservice.backend_scrapify.logic.category.entity.Category;
import com.microservice.backend_scrapify.logic.category.repository.CategoryRepository;
import com.microservice.backend_scrapify.modelResponse.CategoryListStatusResponse;
import com.microservice.backend_scrapify.modelResponse.StatusResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {

    @Autowired
    private SequenceGeneratorService sequenceGeneratorService;

    @Autowired
    private CategoryRepository categoryRepository;

    public StatusResponse save(Category category) {
        Optional<Category> byName = categoryRepository.existByName(category.getName());

        if (category.getId() == null) {
            category.setId(sequenceGeneratorService.getSequenceNumber(Category.CATEGORY_SEQUENCE));
        }

        if (byName.isPresent())
            return new StatusResponse(
                    false,
                    "Category Already Exist By With That Name"
            );

        if (category.getName() == null || category.getName().isEmpty())
            return new StatusResponse(
                    false,
                    "Category Name Can't be Empty"
            );

        return new StatusResponse(
                true,
                "Category Created Successfully",
                categoryRepository.save(category)
        );

    }

    public StatusResponse deleteCategory(Long categoryId) {
        Optional<Category> category = categoryRepository.findById(categoryId);

        if (category.isEmpty())
            return new StatusResponse(
                    false,
                    "Category Not Exist"
            );

        categoryRepository.deleteById(categoryId);

        return new StatusResponse(
                true,
                "Category Deleted Successfully"
        );
    }

    public CategoryListStatusResponse getAll() {
        List<Category> all = categoryRepository.findAll();

        if (all.isEmpty())
            return new CategoryListStatusResponse(
                    false,
                    "Category Not exist"
            );
        return new CategoryListStatusResponse(
                true,
                "Category List Retrieved Successfully",
                all
        );
    }
}
