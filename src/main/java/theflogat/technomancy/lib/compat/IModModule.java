package theflogat.technomancy.lib.compat;

public interface IModModule {

    // Init Event
    void init();

    // PostInit Event
    void postInit();

    // Register Mod Specific Items
    void registerItems();

    // Register Mod Specific Blocks
    void registerBlocks();

    // Register Mod Specific Recipes
    void registerRecipes();
}
