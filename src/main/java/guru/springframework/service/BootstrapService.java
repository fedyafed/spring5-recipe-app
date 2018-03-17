package guru.springframework.service;

import guru.springframework.domain.*;
import guru.springframework.repositories.CategoryRepository;
import guru.springframework.repositories.RecipeRepository;
import guru.springframework.repositories.UnitOfMeasureRepository;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class BootstrapService {
    private RecipeRepository recipeRepository;
    private CategoryRepository categoryRepository;
    private UnitOfMeasureRepository unitOfMeasureRepository;

    public BootstrapService(RecipeRepository recipeRepository, CategoryRepository categoryRepository, UnitOfMeasureRepository unitOfMeasureRepository) {
        this.recipeRepository = recipeRepository;
        this.categoryRepository = categoryRepository;
        this.unitOfMeasureRepository = unitOfMeasureRepository;
    }


    @PostConstruct
    public void init() {
        Recipe recipe = new Recipe();
        recipe.setDescription("The BEST guacamole! So easy to make with ripe avocados, salt, serrano chiles, cilantro and lime. Garnish with red radishes or jicama. Serve with tortilla chips.");
        Set<Category> categories = Stream.of("Dip", "Mexican", "Vegan", "Avocado")
                .map(description -> categoryRepository.findByDescription(description))
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toSet());

        recipe.setCategories(categories);
        recipe.setCookTime(10);
        recipe.setDifficulty(Difficulty.EASY);
        Notes notes = new Notes();
        notes.setRecipe(recipe);
        notes.setRecipeNotes("\n" +
                "MethodShow Photos\n" +
                "\n" +
                "1 Cut avocado, remove flesh: Cut the avocados in half. Remove seed. Score the inside of the avocado with a blunt knife and scoop out the flesh with a spoon. (See How to Cut and Peel an Avocado.) Place in a bowl.\n" +
                "\n" +
                "2 Mash with a fork: Using a fork, roughly mash the avocado. (Don't overdo it! The guacamole should be a little chunky.)\n" +
                "\n" +
                "3 Add salt, lime juice, and the rest: Sprinkle with salt and lime (or lemon) juice. The acid in the lime juice will provide some balance to the richness of the avocado and will help delay the avocados from turning brown.\n" +
                "\n" +
                "Add the chopped onion, cilantro, black pepper, and chiles. Chili peppers vary individually in their hotness. So, start with a half of one chili pepper and add to the guacamole to your desired degree of hotness.\n" +
                "\n" +
                "Remember that much of this is done to taste because of the variability in the fresh ingredients. Start with this recipe and adjust to your taste.\n" +
                "\n" +
                "4 Cover with plastic and chill to store: Place plastic wrap on the surface of the guacamole cover it and to prevent air reaching it. (The oxygen in the air causes oxidation which will turn the guacamole brown.) Refrigerate until ready to serve.\n" +
                "\n" +
                "Chilling tomatoes hurts their flavor, so if you want to add chopped tomato to your guacamole, add it just before serving.\n" +
                "\n" +
                "Variations\n" +
                "\n" +
                "For a very quick guacamole just take a 1/4 cup of salsa and mix it in with your mashed avocados.\n" +
                "\n" +
                "Feel free to experiment! One classic Mexican guacamole has pomegranate seeds and chunks of peaches in it (a Diana Kennedy favorite). Try guacamole with added pineapple, mango, or strawberries (see our Strawberry Guacamole).\n" +
                "\n" +
                "The simplest version of guacamole is just mashed avocados with salt. Don't let the lack of availability of other ingredients stop you from making guacamole.\n" +
                "\n" +
                "To extend a limited supply of avocados, add either sour cream or cottage cheese to your guacamole dip. Purists may be horrified, but so what? It tastes great.\n" +
                "\n" +
                "For a deviled egg version with guacamole, try our Guacamole Deviled Eggs!\n");
        recipe.setNotes(notes);
        HashSet<Ingredient> ingredients = new LinkedHashSet<>();
        Ingredient ingredient = new Ingredient();
        ingredient.setAmount(BigDecimal.valueOf(2));
        ingredient.setDescription("ripe avocados");
        ingredient.setUom(unitOfMeasureRepository.findByDescription("Pcs").get());
        ingredient.setRecipe(recipe);
        ingredients.add(ingredient);

        ingredient = new Ingredient();
        ingredient.setAmount(BigDecimal.valueOf(0.5));
        ingredient.setDescription("Kosher salt");
        ingredient.setUom(unitOfMeasureRepository.findByDescription("Teaspoon").get());
        ingredient.setRecipe(recipe);
        ingredients.add(ingredient);
        /*
        *

    2 ripe avocados
    1/2 teaspoon Kosher salt
    1 Tbsp of fresh lime juice or lemon juice
    2 Tbsp to 1/4 cup of minced red onion or thinly sliced green onion
    1-2 serrano chiles, stems and seeds removed, minced
    2 tablespoons cilantro (leaves and tender stems), finely chopped
    A dash of freshly grated black pepper
    1/2 ripe tomato, seeds and pulp removed, chopped

        * */
        recipe.setIngredients(ingredients);

        recipeRepository.save(recipe);



    }
}
