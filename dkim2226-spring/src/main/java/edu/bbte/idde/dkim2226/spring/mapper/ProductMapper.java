package edu.bbte.idde.dkim2226.spring.mapper;

import edu.bbte.idde.dkim2226.spring.dto.ProductInDTO;
import edu.bbte.idde.dkim2226.spring.dto.ProductOutDTO;
import edu.bbte.idde.dkim2226.spring.model.Product;
import org.mapstruct.IterableMapping;
import org.mapstruct.Mapper;

import java.util.Collection;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    public abstract Product dtoToModel(ProductInDTO dto);

    @IterableMapping(elementTargetType = ProductOutDTO.class)
    public abstract Collection<ProductOutDTO> modelsToDtos(Iterable<Product> models);
}
