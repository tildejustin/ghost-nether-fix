package dev.tildejustin.ghost_nether_fix.client.mixin;

import dev.tildejustin.ghost_nether_fix.client.WorldGenLevelExtension;
import net.minecraft.core.SectionPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.WorldGenRegion;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.stream.Stream;

@Mixin(WorldGenRegion.class)
public abstract class WorldGenRegionMixin implements WorldGenLevel, WorldGenLevelExtension {
    @Shadow
    @Final
    private ServerLevel level;

    @Unique
    private StructureFeatureManager structureFeatureManager;

    @Inject(method = "<init>", at = @At(value = "TAIL"))
    private void getStructureManager(ServerLevel serverLevel, List<ChunkAccess> list, CallbackInfo ci) {
        this.structureFeatureManager = this.level.structureFeatureManager().forWorldGenRegion((WorldGenRegion) (Object) this);
    }

    @Override
    public Stream<? extends StructureStart<?>> startsForFeature(SectionPos sectionPos, StructureFeature<?> structureFeature) {
        return this.structureFeatureManager.startsForFeature(sectionPos, structureFeature);
    }

}
