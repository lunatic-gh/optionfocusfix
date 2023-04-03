package de.chloedev.optionfocusfix.mixin;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ClickableWidget;
import net.minecraft.client.gui.widget.OptionListWidget;
import net.minecraft.client.util.math.MatrixStack;
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
    public void fixFocus(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        this.children.forEach(e -> {
            if (e instanceof ClickableWidget e1) {
                if (!e1.isHovered()) {
                    e1.setFocused(false);
                }
            } else if (e instanceof OptionListWidget e1) {
                e1.children().forEach(e2 -> {
                    if (!e2.hoveredElement(mouseX, mouseY).isPresent()) {
                        e2.setFocused(null);
                    }
                });
            }
        });
    }
}