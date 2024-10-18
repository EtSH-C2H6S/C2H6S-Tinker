package com.c2h6s.etshtinker.Modifiers;

import com.c2h6s.etshtinker.Entities.phantomswordentity;
import com.c2h6s.etshtinker.Modifiers.modifiers.etshmodifieriii;
import com.c2h6s.etshtinker.init.etshtinkerEntity;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

import java.security.SecureRandom;

import static com.c2h6s.etshtinker.etshtinker.EtSHrnd;

public class phantomreforged extends etshmodifieriii {
    public void modifierAfterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt){
        if (modifier.getLevel()>0&&context.getAttacker() instanceof Player player&&context.getTarget() instanceof LivingEntity target&&context.isFullyCharged()){
            Level level1 = player.level;
            phantomswordentity entity =new phantomswordentity(etshtinkerEntity.phantomswordentity.get(),player.level);
            entity.damage =0.25f*damageDealt;
            entity.count =modifier.getLevel()-1;
            entity.target=target;
            entity.setOwner(player);
            entity.setPos(target.getX(),target.getY()+0.5*target.getBbHeight()+3.5,target.getZ());
            player.level.addFreshEntity(entity);
            int i = 0;
            while (i<5){
                SecureRandom random1 =EtSHrnd();
                if(level1.isClientSide) {
                    level1.addParticle(ParticleTypes.SCULK_SOUL, target.getX(), target.getY() + 0.5 * target.getBbHeight() + 3.5, target.getZ(), random1.nextDouble() * 0.04 - 0.02, random1.nextDouble() * 0.04 - 0.02, random1.nextDouble() * 0.04 - 0.02);
                }
                else {
                    ((ServerLevel)level1).sendParticles(ParticleTypes.SCULK_SOUL, target.getX(), target.getY() + 0.5 * target.getBbHeight() + 3.5, target.getZ(), 1,0,0,0, random1.nextDouble() * 0.04 - 0.02);
                }
                i++;
            }
        }
    }
}
