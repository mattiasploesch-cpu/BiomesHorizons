package biomesoplenty.client.gui;

import java.io.File;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.apache.commons.io.FileUtils;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

public class WorldTypeMessageGUI extends GuiScreen {

    private GuiScreen parentGuiScreen;
    private File nameHashFile;
    private String nameHash;
    private String link = "http://www.reddit.com/r/biomesoplenty/comments/1v5ly1/logical_bug_reporting/";
    private static final ResourceLocation bopLogoTexture = new ResourceLocation(
        "biomesoplenty:textures/gui/logo256.png");

    public WorldTypeMessageGUI(GuiScreen parentGuiScreen, File nameHashFile, String nameHash) {
        this.parentGuiScreen = parentGuiScreen;
        this.nameHashFile = nameHashFile;
        this.nameHash = nameHash;
    }

    public void initGui() {
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList
            .add(new GuiButton(0, this.width / 2 - 175, this.height - 24, 350, 20, I18n.format("OK", new Object[0])));
    }

    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }

    protected void actionPerformed(GuiButton button) {
        if (button.enabled && button.id == 0) {
            try {
                this.nameHashFile.createNewFile();
                FileUtils.write(this.nameHashFile, this.nameHash + "StartupWarning".hashCode());
            } catch (IOException e) {
                e.printStackTrace();
            }

            this.mc.displayGuiScreen(this.parentGuiScreen);
        }
    }

    public void drawScreen(int x, int y, float renderPartialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(
            this.fontRendererObj,
            "" + EnumChatFormatting.RED + StatCollector.translateToLocal("warning.bopStartup1"),
            this.width / 2,
            82,
            16777215);
        this.drawCenteredString(
            this.fontRendererObj,
            "" + EnumChatFormatting.RED + StatCollector.translateToLocal("warning.bopStartup2"),
            this.width / 2,
            94,
            16777215);
        this.drawCenteredString(
            this.fontRendererObj,
            "" + EnumChatFormatting.RED + StatCollector.translateToLocal("warning.bopStartup3"),
            this.width / 2,
            106,
            16777215);
        this.drawCenteredString(
            this.fontRendererObj,
            "" + EnumChatFormatting.RED + StatCollector.translateToLocal("warning.bopStartup4"),
            this.width / 2,
            132,
            16777215);
        this.drawCenteredString(
            this.fontRendererObj,
            "" + EnumChatFormatting.RED + StatCollector.translateToLocal("warning.bopStartup5"),
            this.width / 2,
            144,
            16777215);
        this.drawCenteredString(
            this.fontRendererObj,
            "" + EnumChatFormatting.DARK_RED + StatCollector.translateToLocal("warning.bopStartup6"),
            this.width / 2,
            168,
            16777215);
        GL11.glEnable(3042);
        this.mc.getTextureManager()
            .bindTexture(bopLogoTexture);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        this.drawTexturedModalRect(this.width / 2 - 84, 0, 0, 0, 168, 80);
        GL11.glDisable(3042);
        super.drawScreen(x, y, renderPartialTicks);
    }
}
