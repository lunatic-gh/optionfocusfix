package de.chloedev.optionfocusfix.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Screen.class)
public class MixinScreen {

    @Shadow
    @Final
    private List<Element> children;

    @Inject(method = "render", at = @At("TAIL"))
    public void fixFocus(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.children.forEach(e -> {
            if (e instanceof ClickableWidget e1) {
                if (!e1.isHovered()) {
                    e1.setFocused(false);
                }
            } else if (e instanceof ElementListWidget<?> e1) {
                e1.children().forEach(e2 -> {
                    if (e2.hoveredElement(mouseX, mouseY).isEmpty()) {
                        e2.setFocused(null);
                    }
                });
            }
        });
    }
}