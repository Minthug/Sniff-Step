package SniffStep.common.validation;

import SniffStep.common.utils.MessageUtils;
import SniffStep.entity.MemberRole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MemberRoleValidator implements ConstraintValidator<MemberRoleValid, MemberRole> {
    @Override
    public void initialize(MemberRoleValid constraintAnnotation) {
    }

    @Override
    public boolean isValid(MemberRole memberRole, ConstraintValidatorContext context) {
        if (memberRole == null) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(MessageUtils.getMessage("memberRole.valid.message"))
                    .addConstraintViolation();
            return false;
        }
        return true;
    }
}

