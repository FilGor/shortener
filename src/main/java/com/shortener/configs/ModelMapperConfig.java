package com.shortener.configs;

import com.shortener.models.ShortUrl;
import com.shortener.models.dto.ShortUrlDto;
import com.shortener.models.dto.ShortUrlMetadataDto;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@Configuration
public class ModelMapperConfig {


    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<ShortUrl, String> toFullUrl = ctx -> {
            String code = ctx.getSource().getShortCode();
            return ServletUriComponentsBuilder
                    .fromCurrentContextPath()
                    .path("/")
                    .path(code)
                    .toUriString();
        };

        TypeMap<ShortUrl, ShortUrlDto> typeMap =
                mapper.createTypeMap(ShortUrl.class, ShortUrlDto.class);

        typeMap.addMappings(mapperConfig ->
                mapperConfig.using(toFullUrl)
                        .map(src -> src, ShortUrlDto::setShortenedUrl)
        );
        
        TypeMap<ShortUrl, ShortUrlMetadataDto> metaMap =
                mapper.createTypeMap(ShortUrl.class, ShortUrlMetadataDto.class);
        metaMap.addMappings(cfg -> {
            cfg.using(toFullUrl)
                    .map(src -> src, ShortUrlMetadataDto::setShortenedUrl);
            cfg.map(ShortUrl::getOriginalUrl,    ShortUrlMetadataDto::setOriginalUrl);
            cfg.map(ShortUrl::getShortCode,      ShortUrlMetadataDto::setShortCode);
            cfg.map(ShortUrl::getVisitCount,     ShortUrlMetadataDto::setVisitCount);
            cfg.map(ShortUrl::getCreatedDate,    ShortUrlMetadataDto::setCreatedDate);
            cfg.map(ShortUrl::getLastModifiedDate, ShortUrlMetadataDto::setLastModifiedDate);
            cfg.map(ShortUrl::getCreatedBy,      ShortUrlMetadataDto::setCreatedBy);
            cfg.map(ShortUrl::getLastModifiedBy, ShortUrlMetadataDto::setLastModifiedBy);
        });

        return mapper;
    }
}
