package theflogat.technomancy.asm;

import java.util.Map;

import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import theflogat.technomancy.lib.Ref;

@IFMLLoadingPlugin.MCVersion("1.12")
@IFMLLoadingPlugin.Name(Ref.MOD_NAME + " Core")
public class TechnomancyCoreLoader implements IFMLLoadingPlugin {

	@Override
	public String[] getASMTransformerClass() {
		return new String[] {TechnomancyCoreTransformer.class.getName()};
	}

	@Override
	public String getModContainerClass() {
		return TechnomancyCore.class.getName();
	}

	@Override
	public String getSetupClass() {
		return null;
	}

	@Override
	public void injectData(Map<String, Object> data) {}

	@Override
	public String getAccessTransformerClass() {
		return null;
	}

}
