package com.example.knifepack.client;

import com.example.knifepack.Knife;
import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import org.lwjgl.glfw.GLFW;

public class KnifePackClient implements ClientModInitializer {
    private static final String CATEGORY = "key.categories.knifepack";

    private static Knife equipped = Knife.BUTTERFLY;

    /** Opens the knife menu. Bindable under Controls > Key Binds. */
    private static KeyMapping menuKey;

    /** Spins the held knife while held down. Bindable under Controls > Key Binds. */
    private static KeyMapping spinKey;

    @Override
    public void onInitializeClient() {
        menuKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.knifepack.menu", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_K, CATEGORY));

        spinKey = KeyBindingHelper.registerKeyBinding(new KeyMapping(
                "key.knifepack.spin", InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) {
                return;
            }
            // Open the menu on press, but only from the game screen.
            while (menuKey.consumeClick()) {
                if (client.screen == null) {
                    client.setScreen(new KnifeSelectScreen());
                }
            }
            // Hold-to-spin: spins only while the spin key is physically held and no GUI is open.
            boolean held = spinKey.isDown() && client.screen == null;
            SpinState.tick(held);
        });
    }

    public static Knife equipped() {
        return equipped;
    }

    public static void equip(Knife knife) {
        equipped = knife;
    }
}
