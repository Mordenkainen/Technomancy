package theflogat.technomancy.client.gui.tome.render.pages;

import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import org.lwjgl.opengl.GL11;

import theflogat.technomancy.client.gui.tome.GuiTomeTemplate;

public abstract class PageRecipe extends PageRender {

    protected static ItemStack glass = new ItemStack(Blocks.glass);

    static {
        glass.stackTagCompound = new NBTTagCompound();
        glass.stackTagCompound.setBoolean("trans", true);
    }

    @Override
    public void render(GuiTomeTemplate gui, int left, int top, boolean page2) {

        ItemStack[] inputs = getRecipe();

        int x = left + (page2 ? 210 : 100);
        int y = top + 100;
        GL11.glDisable(GL11.GL_LIGHTING);
        try {
            gui.getItemRend().renderItemAndEffectIntoGUI(gui.getFont(), gui.getMinecraft().getTextureManager(), getOutput(), x, y);
            gui.getItemRend().renderItemOverlayIntoGUI(gui.getFont(), gui.getMinecraft().getTextureManager(), getOutput(), x, y, (getOutput().stackSize == 1 ? "" : Integer.toString(getOutput().stackSize)));
        } catch (Exception e) {}

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                GL11.glDisable(GL11.GL_LIGHTING);
                try {
                    int newx = left + 46 + (j * 18) + (page2 ? 110 : 0);
                    int newy = top + 82 + (i * 18);
                    ItemStack items = inputs[(i * 3) + j];
                    boolean isTrans = items.stackTagCompound != null && items.stackTagCompound.getBoolean("trans");
                    if (isTrans) {
                        System.out.println("Transparent");
                        GL11.glEnable(GL11.GL_LIGHTING);
                    }
                    gui.getItemRend().renderItemAndEffectIntoGUI(gui.getFont(), gui.getMinecraft().getTextureManager(), items, newx, newy);
                    gui.getItemRend().renderItemOverlayIntoGUI(gui.getFont(), gui.getMinecraft().getTextureManager(), items, newx, newy, displayStackSize() && items.stackSize > 1 ? Integer.toString(items.stackSize) : "");
                    if (isTrans) {
                        GL11.glDisable(GL11.GL_LIGHTING);
                    }
                } catch (Exception e) {
                    // e.printStackTrace();
                }
            }
        }
    }

    public abstract ItemStack[] getRecipe();

    public abstract ItemStack getOutput();

    public boolean displayStackSize() {
        return false;
    }
}
