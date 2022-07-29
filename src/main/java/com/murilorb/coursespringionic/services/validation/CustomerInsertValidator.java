package com.murilorb.coursespringionic.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import com.murilorb.coursespringionic.domains.dtos.CustomerNewDTO;
import com.murilorb.coursespringionic.domains.enums.CustomerType;
import com.murilorb.coursespringionic.resources.exception.FieldMessage;
import com.murilorb.coursespringionic.services.validation.utils.BR;

public class CustomerInsertValidator implements ConstraintValidator<CustomerInsert, CustomerNewDTO> {

	@Override
	public void initialize(CustomerInsert ann) {
	}

	@Override
	public boolean isValid(CustomerNewDTO objDto, ConstraintValidatorContext context) {
		List<FieldMessage> list = new ArrayList<>();

		// inclua os testes aqui, inserindo erros na lista
		//
		// caso um cliente seja pessoa fisica (PRIVATE INDIVIDUAL), sera validado CPF
		if (objDto.getType().equals(CustomerType.PRIVATE_INDIVIDUAL.getCod())
				&& !BR.isValidCPF(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CPF inválido"));
		}
		// caso um cliente seja pessoa juridica (LEGAL ENTITY), sera validado CNPJ
		if (objDto.getType().equals(CustomerType.LEGAL_ENTITY.getCod()) && !BR.isValidCNPJ(objDto.getCpfOrCnpj())) {
			list.add(new FieldMessage("cpfOrCnpj", "CNPJ inválido"));
		}

		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}

}
