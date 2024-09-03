package de.chloedev.optionfocusfix.mixin;

import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.ParentElement;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ParentElement.class)

public interface MixinParentElement {

    @Shadow
    List<? extends Element> children();

    @Inject(method = "mouseClicked", at = @At(value = "RETURN", ordinal = 0))
    default void fixFocus$clicked$0(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        this.children().forEach(element -> {
            if (!(element instanceof TextFieldWidget)) element.setFocused(false);
        });
    }

    @Inject(method = "mouseReleased", at = @At("RETURN"))
    default void fixFocus$released(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        this.children().forEach(element -> {
            if (!(element instanceof TextFieldWidget)) element.setFocused(false);
        });
    }
}
