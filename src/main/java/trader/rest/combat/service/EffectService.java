package trader.rest.combat.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import trader.rest.combat.entity.*;
import trader.rest.combat.entity.Character;
import trader.rest.combat.entity.Effect;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EffectService {

    private void applyCoreCharacterEffects(Character self, Character target, Effect effect, EffectResult effectResult)  {
        log.debug("Applying any '{}' effects to '{}'.", effect.getName(), self.getName());
        effect.getSelfStatModifier().keySet().forEach(e -> {
            if (e.num < 7) {
                int change = effect.getSelfStatModifier().get(e);
                self.applyStatusChange(e, change);
                effectResult.recordSelfStatChange(e, change);
            }
        });

        if (target != null) {
            log.debug("Applying any '{}' effect to '{}'.", effect.getName(), self.getName());
            effect.getTargetStatModifier().keySet().forEach(e -> {
                if (e.num < 7) {
                    int change = effect.getTargetStatModifier().get(e);
                    target.applyStatusChange(e, change);
                    effectResult.recordTargetStatChange(e, change);
                }
        });
    }}

    public List<EffectResult> calculateAndApplyOnAttackEffects(Character belligerent, Character defender) {
        List<EffectResult> effectResults = new ArrayList<>();

//        for (Effect effect: belligerent.getEffects().keySet()) {
//            EffectResult newReport = EffectResult.builder()
//                    .atkDmgModifier(effect.getSelfStatModifier().getOrDefault(StatTypeEnum.DMG, 0))
//                    .atkHitModifier(effect.getSelfStatModifier().getOrDefault(StatTypeEnum.HIT, 0))
//                    .defDmgModifier(effect.getTargetStatModifier().getOrDefault(StatTypeEnum.DMG, 0))
//                    .defHitModifier(effect.getTargetStatModifier().getOrDefault(StatTypeEnum.HIT, 0))
//                    .effectTurnsRemaining(belligerent.getEffects().get(effect).getTurnsRemaining())
//                    .effectName(effect.getName())
//                    .build();
//
//            applyCoreCharacterEffects(belligerent, defender, effect, newReport);
//            effectResults.add(newReport);
//            belligerent.incrementEffect(effect);
//        }

        return effectResults;
    }

    public List<Double> calculatePostEffectAttackValues(Double atkDmg, Double atkHit, Double defDmg, Double defHit, List<EffectResult> effectResults) {
        for (EffectResult effectResult: effectResults) {
            atkDmg += effectResult.getAtkDmgModifier();
            atkHit += effectResult.getAtkHitModifier();
            defDmg += effectResult.getDefDmgModifier();
            defHit += effectResult.getDefHitModifier();
        }
        return List.of(atkDmg, atkHit, defDmg, defHit);
    }

}
