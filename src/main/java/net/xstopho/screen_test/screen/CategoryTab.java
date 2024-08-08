package net.xstopho.screen_test.screen;

import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.tabs.Tab;
import net.minecraft.client.gui.navigation.ScreenRectangle;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;

public class CategoryTab implements Tab {

    protected final CategoryEntryList list;

    private final Component component;

    public CategoryTab(Screen screen, Component component) {
        this.component = component;

        this.list = new CategoryEntryList(Minecraft.getInstance(), screen.width,
                screen.height, 0, 24, createEntries());
    }

    protected List<CategoryEntry> createEntries() {
        List<CategoryEntry> entries = new LinkedList<>();

        for (int i = 0; i <= 25; i++) {
            if (i % 2 == 0) entries.add(new CategoryEntry(Component.literal("Test Category").withStyle(ChatFormatting.GOLD)));
            else {
                for (int j = 0; j <= 3; j++) {
                    entries.add(new CategoryEntry(component, "Entry " + i * j));
                }
            }
        }

        return entries;
    }

    @Override
    public Component getTabTitle() {
        return component;
    }

    @Override
    public void visitChildren(Consumer<AbstractWidget> consumer) {
        consumer.accept(this.list);
    }

    @Override
    public void doLayout(ScreenRectangle screenRectangle) {
        this.list.setRectangle(screenRectangle.width(), screenRectangle.height(), screenRectangle.left(), screenRectangle.top());
    }
}
