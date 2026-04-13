package cn.liukay.behavior;

import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import org.jspecify.annotations.NonNull;

public class LightningStick extends Item {

    public LightningStick(Properties properties) {
        super(properties);
    }

    @Override
    public @NonNull InteractionResult useOn(UseOnContext context) {
        if (context.getLevel().isClientSide()) {
            return InteractionResult.SUCCESS;
        }

        // 计算玩家与点击位置的距离
        if (context.getPlayer() != null) {
            double distance = context.getPlayer().position().distanceTo(context.getClickLocation());
            if (distance < 3.5) {
                return InteractionResult.PASS;
            }
        }

        // 创建闪电实体
        LightningBolt lightningBolt = new LightningBolt(EntityType.LIGHTNING_BOLT, context.getLevel());
        // 设置闪电位置
        lightningBolt.setPos(context.getClickLocation());
        // 添加闪电实体到世界
        context.getLevel().addFreshEntity(lightningBolt);

        // 返回成功结果
        return InteractionResult.SUCCESS;
    }

    @Override
    public void hurtEnemy(@NonNull ItemStack itemStack, @NonNull LivingEntity mob, @NonNull LivingEntity attacker) {
        // 获取世界实例
        var level = mob.level();
        if (level.isClientSide()) return;

        // 计算实体与攻击者之间的距离（没什么用）
        double distance = mob.position().distanceTo(attacker.position());
        if (distance < 2) return;

        // 创建闪电实体
        LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, level);
        // 设置闪电位置
        lightning.setPos(mob.position());
        // 添加闪电实体到世界
        level.addFreshEntity(lightning);
    }
}
