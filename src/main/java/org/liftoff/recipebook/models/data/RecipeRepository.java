package org.liftoff.recipebook.models.data;

import org.liftoff.recipebook.models.Recipe;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecipeRepository extends CrudRepository<Recipe,Integer> {
    Recipe findById(int id);

}
