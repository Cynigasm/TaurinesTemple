/**
 * 
 * Class Notes Section
 *
 */
package com.cynigasm.projects.minecraft.social;

import net.minecraft.server.v1_11_R1.BlockPosition;
import net.minecraft.server.v1_11_R1.ContainerAnvil;
import net.minecraft.server.v1_11_R1.EntityHuman;

/**
 * @author Lewysryan
 * @website https://bukkit.org/threads/1-8-open-an-anvil-inventory-not-much-code.328178/
 * Jun 10, 2017
 */
public final class FakeAnvil extends ContainerAnvil {

public FakeAnvil(EntityHuman entityHuman) {    
    super(entityHuman.inventory, entityHuman.world, new BlockPosition(0,0,0), entityHuman);
}


@Override
public boolean a(EntityHuman entityHuman) {    
return true;
    }
}