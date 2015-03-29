package theflogat.technomancy.lib.compat.botania;

import vazkii.botania.api.BotaniaAPI;
import vazkii.botania.api.lexicon.IAddonEntry;
import vazkii.botania.api.lexicon.LexiconCategory;
import vazkii.botania.api.lexicon.LexiconEntry;

public class TechnoLexicon extends LexiconEntry implements IAddonEntry {
	
	public TechnoLexicon(String unlocalizedName, LexiconCategory category) {
		super(unlocalizedName, category);
		BotaniaAPI.addEntry(this, category);
	}

	@Override
	public String getSubtitle() {
		return "[Technomancy]";
	}
}
