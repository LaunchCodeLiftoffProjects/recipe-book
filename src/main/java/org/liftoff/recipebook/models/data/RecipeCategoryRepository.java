package org.liftoff.recipebook.models.data;


import org.liftoff.recipebook.models.RecipeCategory;
import org.springframework.data.repository.CrudRepository;

public interface RecipeCategoryRepository extends CrudRepository<RecipeCategory,Integer> {
}
