package com.github.ustc_zzzz.fmltutor.entity;

import com.github.ustc_zzzz.fmltutor.FMLTutor;
import com.github.ustc_zzzz.fmltutor.client.entity.render.RenderGoldenChicken;
import com.github.ustc_zzzz.fmltutor.item.ItemLoader;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderSnowball;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EntityLoader
{
    public EntityLoader()
    {
        registerEntity(EntityGoldenChicken.class, "GoldenChicken", 80, 3, true);
        registerEntityEgg(EntityGoldenChicken.class, 0xffff66, 0x660000);
        registerEntitySpawn(EntityGoldenChicken.class, 8, 2, 4, EnumCreatureType.CREATURE, BiomeGenBase.plains,
                BiomeGenBase.desert);
        registerEntity(EntityGoldenEgg.class, "GoldenEgg", 64, 10, true);
    }

    private static void registerEntityEgg(Class<? extends Entity> entityClass, int eggPrimary, int eggSecondary)
    {
        EntityRegistry.registerEgg(entityClass, eggPrimary, eggSecondary);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String name, int trackingRange,
            int updateFrequency, boolean sendsVelocityUpdates)
    {
        int entityID = EntityRegistry.findGlobalUniqueEntityId();

        EntityRegistry.registerGlobalEntityID(entityClass, name, entityID);
        EntityRegistry.registerModEntity(entityClass, name, entityID, FMLTutor.instance, trackingRange, updateFrequency,
                sendsVelocityUpdates);
    }

    private static void registerEntitySpawn(Class<? extends Entity> entityClass, int spawnWeight, int min,
            int max, EnumCreatureType typeOfCreature, BiomeGenBase... biomes)
    {
        if (EntityLiving.class.isAssignableFrom(entityClass))
        {
            Class<? extends EntityLiving> entityLivingClass = entityClass.asSubclass(EntityLiving.class);
            EntityRegistry.addSpawn(entityLivingClass, spawnWeight, min, max, typeOfCreature, biomes);
        }
    }

    @SideOnly(Side.CLIENT)
    public static void registerRenders()
    {
        registerEntityRender(EntityGoldenChicken.class,
                new RenderGoldenChicken(Minecraft.getMinecraft().getRenderManager()));
        registerEntityRender(EntityGoldenEgg.class,
                new RenderSnowball(Minecraft.getMinecraft().getRenderManager(), ItemLoader.goldenEgg,
                        Minecraft.getMinecraft().getRenderItem()));
    }

    @SideOnly(Side.CLIENT)
    private static void registerEntityRender(Class<? extends Entity> entityClass, Render renderer)
    {
        RenderingRegistry.registerEntityRenderingHandler(entityClass, renderer);
    }
}
