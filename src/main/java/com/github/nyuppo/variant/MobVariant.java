package com.github.nyuppo.variant;

import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.biome.Biome;

import java.util.ArrayList;
import java.util.List;

public class MobVariant {
    private final Identifier identifier;
    private final int weight;
    private final List<VariantModifier> modifiers;

    public MobVariant(Identifier identifier, int weight) {
        this.identifier = identifier;
        this.weight = weight;
        this.modifiers = new ArrayList<VariantModifier>();
    }

    public MobVariant(Identifier identifier, int weight, List<VariantModifier> modifiers) {
        this.identifier = identifier;
        this.weight = weight;
        this.modifiers = modifiers;
    }

    public MobVariant addModifier(VariantModifier modifier) {
        this.modifiers.add(modifier);
        return this;
    }

    public Identifier getIdentifier() {
        return this.identifier;
    }

    public int getWeight() {
        return this.weight;
    }

    public boolean isShiny() {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof ShinyModifier) {
                return true;
            }
        }
        return false;
    }

    public boolean shouldDiscard(Random random) {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof DiscardableModifier) {
                return ((DiscardableModifier) modifier).shouldDiscard(random);
            }
        }
        return false;
    }

    public boolean isInSpawnBiome(RegistryEntry<Biome> biome) {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof SpawnableBiomesModifier) {
                return ((SpawnableBiomesModifier) modifier).canSpawnInBiome(biome);
            }
        }
        return true;
    }

    public boolean canBreed(MobVariant parent1, MobVariant parent2) {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof BreedingResultModifier) {
                return ((BreedingResultModifier) modifier).validParents(parent1, parent2);
            }
        }
        return false;
    }

    public boolean hasSpawnableBiomeModifier() {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof SpawnableBiomesModifier) {
                return true;
            }
        }

        return false;
    }

    public boolean hasBreedingResultModifier() {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof BreedingResultModifier) {
                return true;
            }
        }

        return false;
    }

    public boolean hasCustomEyes() {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof CustomEyesModifier) {
                return true;
            }
        }
        return false;
    }

    public boolean hasCustomWool() {
        for (VariantModifier modifier : this.modifiers) {
            if (modifier instanceof CustomWoolModifier) {
                return true;
            }
        }
        return false;
    }
}
