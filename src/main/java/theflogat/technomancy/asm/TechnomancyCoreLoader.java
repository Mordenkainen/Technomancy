package theflogat.technomancy.asm;

import java.util.Map;

import cpw.mods.fml.relauncher.IFMLLoadingPlugin;

import theflogat.technomancy.lib.Reference;

@IFMLLoadingPlugin.MCVersion("1.7.10")
@IFMLLoadingPlugin.Name(Reference.MOD_NAME + " Core")
public class TechnomancyCoreLoader implements IFMLLoadingPlugin {

    @Override
    public String[] getASMTransformerClass() {
        return new String[] { TechnomancyCoreTransformer.class.getName() };
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
    public void injectData(final Map<String, Object> data) {}

    @Override
    public String getAccessTransformerClass() {
        return null;
    }

}
