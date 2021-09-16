package trader.rest.combat.entity;

import trader.rest.combat.exception.ValidationException;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;

public class Utils {
    public static void validate(Object obj) throws ValidationException {
        final Validator validator = Validation
                .buildDefaultValidatorFactory()
                .getValidator();

        boolean validationSuccessful = obj != null && validator.validate(obj)
                .stream()
                .map(ConstraintViolation::getMessage)
                .findAny()
                .isEmpty();

        if (!validationSuccessful) {
            throw new ValidationException(validator.validate(obj));
        }

        if (obj instanceof Character) {
            validate(((Character) obj).abilityModifier);
            validate(((Character) obj).inventory.armor);
            validate(((Character) obj).armorStats);
//            validate(((Character) obj).effects);
            validate(((Character) obj).body);}

        if (obj instanceof AttackRequest) {
            validate(((AttackRequest) obj).belligerents);
            validate(((AttackRequest) obj).defenders);
            validate(((AttackRequest) obj).ability);
        }
    }
}