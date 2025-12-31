package com.github.gasfgrv.graalvm.infrastructure.mapper;

import com.github.gasfgrv.graalvm.domain.model.Endereco;
import com.github.gasfgrv.graalvm.infrastructure.dto.ViaCepResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EnderecoMapper {

    @Mapping(source = "logradouro", target = "rua")
    @Mapping(source = "localidade", target = "cidade")
    Endereco retonarEndereco(ViaCepResponse resposta);

}
