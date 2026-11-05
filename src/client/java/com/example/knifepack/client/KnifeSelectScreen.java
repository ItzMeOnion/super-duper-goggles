package com.example.knifepack.client;

import com.example.knifepack.Knife;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

/** Menu for choosing which knife is equipped. Selection is client-side only. */
public class KnifeSelectScreen extends Screen {
    private static final int BUTTON_WIDTH = 160;
    private static final int BUTTON_HEIGHT = 20;
    private static final int SPACING = 24;

    public KnifeSelectScreen() {
        super(Component.literal("Choose Knife"));
    }

    @Override
    protected void init() {
        int x = this.width / 2 - BUTTON_WIDTH / 2;
        int startY = this.height / 2 - (Knife.values().length * SPACING) / 2;

        for (int i = 0; i < Knife.values().length; i++) {
            Knife knife = Knife.values()[i];
            int y = startY + i * SPACING;
            this.addRenderableWidget(Button.builder(
                    knifeLabel(knife),
                    button -> {
                        KnifePackClient.equip(knife);
                        this.onClose();
                    })
                .bounds(x, y, BUTTON_WIDTH, BUTTON_HEIGHT)
                .build());
        }
    }

    private Component knifeLabel(Knife knife) {
        boolean selected = KnifePackClient.equipped() == knife;
        String name = knife.displayName();
        return Component.literal(selected ? "> " + name + " <" : name);
    }

    @Override
    public void render(GuiGraphics graphics, int mouseX, int mouseY, float delta) {
        super.render(graphics, mouseX, mouseY, delta);
        graphics.drawCenteredString(this.font, this.title,
                this.width / 2, this.height / 2 - (Knife.values().length * SPACING) / 2 - 16, 0xFFFFFF);
    }

    @Override
    public boolean isPauseScreen() {
        return false;
    }
}
