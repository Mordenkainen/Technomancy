package theflogat.technomancy.lib.compat;

public interface IModModule {

	public void preInit();

	// Init Event
	public void Init();
	
	// PostInit Event
	public void PostInit();
	
	// Register Mod Specific Items
	public void RegisterItems();
	
	// Register Mod Specific Blocks
	public void RegisterBlocks();
	
	// Register Mod Specific Recipes
	public void RegisterRecipes();
}
