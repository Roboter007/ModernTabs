package dev.moderntabs.mixin;

import dev.moderntabs.interfaces.SpriteContentsExtension;
import net.minecraft.client.renderer.texture.SpriteContents;
import net.minecraft.client.renderer.texture.SpriteTicker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SpriteContents.class)
public class SpriteContentsMixin implements SpriteContentsExtension {

    @Unique
    private SpriteContents.Ticker moderntabs$ticker = null;

    @Override
    public SpriteContents.Ticker moderntabs$getTicker() {
        return this.moderntabs$ticker;
    }

    @Override
    public void moderntabs$setTicker(final SpriteContents.Ticker ticker) {
        this.moderntabs$ticker = ticker;
    }

    @Inject(method = "createTicker", at = @At("RETURN"))
    private void moderntabs$createTicker(final CallbackInfoReturnable<SpriteTicker> cir) {
        this.moderntabs$setTicker((SpriteContents.Ticker) cir.getReturnValue());
    }
}
