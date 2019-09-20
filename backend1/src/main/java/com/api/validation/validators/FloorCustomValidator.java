package com.api.validation.validators;

import com.api.validation.annotations.ValidFloor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class FloorCustomValidator implements ConstraintValidator<ValidFloor, MultipartFile> {

   private String regex;
   private short nameLength;

   public void initialize(ValidFloor constraint) {
      regex = constraint.regex();
      nameLength = constraint.nameLength();
   }

   public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
      if(nameLength != 0 && file.getName().length() > nameLength)
         return false;
      return file.getOriginalFilename().matches(regex);
   }
}
