package dev.tildejustin.ghost_nether_fix.client.mixin;

import dev.tildejustin.ghost_nether_fix.client.WorldGenLevelExtension;
import net.minecraft.core.BlockPos;
import net.minecraft.core.SectionPos;
import net.minecraft.world.level.StructureFeatureManager;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.LakeFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.BlockStateConfiguration;
import net.minecraft.world.level.levelgen.structure.StructureStart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;
import java.util.stream.Stream;

@Mixin(LakeFeature.class)
public abstract class LakeFeatureMixin {
    @Unique
    private WorldGenLevel worldGenLevel;

    @Inject(
            method = "place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureFeatureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/feature/configurations/BlockStateConfiguration;)Z",
            at = @At(
                    value = "HEAD"
            )
    )
    private void captureWorldGenLevel(WorldGenLevel worldGenLevel, StructureFeatureManager structureFeatureManager, ChunkGenerator chunkGenerator, Random random, BlockPos blockPos, BlockStateConfiguration blockStateConfiguration, CallbackInfoReturnable<Boolean> cir) {
        this.worldGenLevel = worldGenLevel;
    }

    @Redirect(
            method = "place(Lnet/minecraft/world/level/WorldGenLevel;Lnet/minecraft/world/level/StructureFeatureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Ljava/util/Random;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/levelgen/feature/configurations/BlockStateConfiguration;)Z",
            at = @At(
                    value = "INVOKE",
                    target = "Lnet/minecraft/world/level/StructureFeatureManager;startsForFeature(Lnet/minecraft/core/SectionPos;Lnet/minecraft/world/level/levelgen/feature/StructureFeature;)Ljava/util/stream/Stream;"
            )
    )
    private Stream<? extends StructureStart<?>> redirectStartsForFeature(StructureFeatureManager instance, SectionPos sectionPos, StructureFeature<?> structureFeature) {
        return ((WorldGenLevelExtension) this.worldGenLevel).startsForFeature(sectionPos, structureFeature);
    }
}
