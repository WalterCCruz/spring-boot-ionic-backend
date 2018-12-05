package com.waltercruz.cursomc.services.validation;


import com.waltercruz.cursomc.DTO.ClienteNewDTO;
import com.waltercruz.cursomc.domain.Enums.TipoCliente;
import com.waltercruz.cursomc.resources.exception.FieldMessage;
import com.waltercruz.cursomc.services.validation.utils.BR;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.ArrayList;
import java.util.List;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> {
    @Override
    public void initialize(ClienteInsert ann) {
    }


    @Override
    public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
        List<FieldMessage> list = new ArrayList<>();


        if (objDto.equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage(("cpfOucnpj"), "CPF Inválido"));
        }


        if (objDto.equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
            list.add(new FieldMessage(("cpfOucnpj"), "CNPJ Inválido"));
        }


        for (FieldMessage e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.getMessage())
                    .addPropertyNode(e.getFieldMessage()).addConstraintViolation();
        }
        return list.isEmpty();
    }
}
