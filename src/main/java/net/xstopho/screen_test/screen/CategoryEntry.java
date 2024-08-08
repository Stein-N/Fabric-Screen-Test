package net.xstopho.screen_test.screen;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.Component;
import net.xstopho.screen_test.ScreenTest;

import java.util.ArrayList;
import java.util.List;

public class CategoryEntry extends ContainerObjectSelectionList.Entry<CategoryEntry> {

    private Component label;
    private Component category;

    private final List<AbstractWidget> children = new ArrayList<>();
    private final Font font = Minecraft.getInstance().font;

    private EditBox editBox;
    private Button button;

    public CategoryEntry(Component category) {
        this.category = category;
    }

    public CategoryEntry(Component tabTitle, String entryTitle) {
        this.editBox = new EditBox(font, 0, 0, 150, 18, Component.literal(entryTitle));
        this.editBox.setValue("TEST");

        this.button = Button.builder(Component.literal(entryTitle), onPress -> ScreenTest.LOGGER.error("Button from Entry '{}' was pressed", entryTitle)).bounds(0, 0, 50, 20).build();
        this.label = Component.literal(tabTitle.getString() + " " + entryTitle);

        this.children.add(editBox);
        this.children.add(button);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float partialTick) {
        if (category != null) {
            guiGraphics.drawString(font, category, xPos + (entryWidth / 2) - (font.width(category.getString()) / 2), yPos + 6, -1, false);
        }

        if (editBox != null && button != null) {
            guiGraphics.drawString(font, label, xPos, yPos + 6, -1, false);

            editBox.setX(xPos + entryWidth - 150);
            editBox.setY(yPos + 1);

            button.setX(xPos + entryWidth - button.getWidth() - 2);
            button.setY(yPos);

            editBox.setWidth(150 - button.getWidth() - 2);

            editBox.render(guiGraphics, mouseX, mouseY, partialTick);
            button.render(guiGraphics, mouseX, mouseY, partialTick);
        }
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifier) {
        if (this.getFocused() instanceof EditBox) {
            return this.getFocused().keyPressed(keyCode, scanCode, modifier);
        }
        return super.keyPressed(keyCode, scanCode, modifier);
    }

    @Override
    public boolean charTyped(char c, int i) {
        if (this.getFocused() instanceof EditBox) {
            return this.getFocused().charTyped(c, i);
        }
        return false;
    }

    @Override
    public List<? extends NarratableEntry> narratables() {
        return this.children;
    }


    @Override
    public List<? extends GuiEventListener> children() {
        return this.children;
    }
}
