package dev.moderntabs.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import dev.moderntabs.interfaces.TickerExtension;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(targets = "net.minecraft.client.renderer.texture.SpriteContents$Ticker")
public class SpriteContentsTickerMixin implements TickerExtension {

    @Unique
    private boolean moderntabs$playing = true;

    @WrapOperation(method = "tickAndUpload", at = @At(value = "FIELD", target = "Lnet/minecraft/client/renderer/texture/SpriteContents$Ticker;frame:I", opcode = 181 /* PUTFIELD */))
    private void moderntabs$putField(final SpriteContents.Ticker instance, final int value, final Operation<Void> original) {
        if (this.moderntabs$isPlaying()) {
            original.call(instance, value);
        }
    }

    @Override
    public void moderntabs$setPlaying(final boolean playing) {
        this.moderntabs$playing = playing;
    }

    @Override
    public boolean moderntabs$isPlaying() {
        return this.moderntabs$playing;
    }
}
