package net.xstopho.config_screen.screen.entries.primitiv;

import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.xstopho.config_screen.ConfigScreenConstants;
import net.xstopho.config_screen.config.TestConfigEntry;
import net.xstopho.config_screen.screen.entries.base.ValueEntry;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class BooleanValueEntry extends ValueEntry<Boolean> {

    private final Button entryButton;

    private boolean buttonState;

    private final Component enabled = ConfigScreenConstants.BOOLEAN_ENABLED;
    private final Component disabled = ConfigScreenConstants.BOOLEAN_DISABLED;


    public BooleanValueEntry(Component entryLabel, @Nullable Component entryTooltip, TestConfigEntry<Boolean> entry) {
        super(entryLabel, entryTooltip, entry);
        this.buttonState = entry.getConfigValue();

        this.entryButton = Button.builder(buttonState ? enabled : disabled, this::changeButtonState).bounds(0, 0, getValueWidgetWidth(), 20).build();

        this.children.add(entryButton);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int index, int yPos, int xPos, int entryWidth, int entryHeight,
                       int mouseX, int mouseY, boolean hovered, float partialTick) {
        super.render(guiGraphics, index, yPos, xPos, entryWidth, entryHeight, mouseX, mouseY, hovered, partialTick);

        entryButton.setX(xPos + entryWidth - getValueWidgetWidth());
        entryButton.setY(yPos);
        entryButton.setWidth(getCorrectedWidgetWidth());

        entryButton.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    private void changeButtonState(Button button) {
        buttonState = !buttonState;

        if (buttonState) button.setMessage(enabled);
        else button.setMessage(disabled);

        setUndoState(!Objects.equals(buttonState, entry.getConfigValue()));
    }

    @Override
    public Boolean getChangedValue() {
        return buttonState;
    }

    @Override
    public void undoChanges() {
        entryButton.setMessage(entry.getConfigValue() ? enabled : disabled);
        buttonState = entry.getConfigValue();
        setUndoState(false);
    }

    @Override
    public void resetValues() {
        entryButton.setMessage(entry.getDefaultValue() ? enabled : disabled);
        buttonState = entry.getDefaultValue();
        setUndoState(!Objects.equals(buttonState, entry.getConfigValue()));
    }
}
