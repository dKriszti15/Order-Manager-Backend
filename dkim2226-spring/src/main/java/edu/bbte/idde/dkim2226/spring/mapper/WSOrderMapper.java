package edu.bbte.idde.dkim2226.spring.mapper;

import edu.bbte.idde.dkim2226.spring.dto.WSOrderInDTO;
import edu.bbte.idde.dkim2226.spring.dto.WSOrderOutDTO;
import edu.bbte.idde.dkim2226.spring.model.WebshopOrder;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring", imports = java.time.LocalDateTime.class)
public abstract class WSOrderMapper {
    @Mapping(target = "orderDate", expression = "java(new java.util.Date())")
    public abstract WebshopOrder dtoToModel(WSOrderInDTO dto);

    @IterableMapping(elementTargetType = WSOrderOutDTO.class)
    public abstract Collection<WSOrderOutDTO> modelsToOutDtos(Iterable<WebshopOrder> model);

    public abstract WSOrderOutDTO modelToOutDto(WebshopOrder model);
}


