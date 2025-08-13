package com.shortener.configs;

import com.shortener.models.ShortUrl;
import com.shortener.models.dto.ShortUrlDto;
import com.shortener.models.dto.ShortUrlMetadataDto;
import com.shortener.service.UrlBuilderService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ModelMapperConfig {

    private final UrlBuilderService urlBuilderService;

    public ModelMapperConfig(UrlBuilderService urlBuilderService) {
        this.urlBuilderService = urlBuilderService;
    }

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper mapper = new ModelMapper();

        Converter<ShortUrl, String> toFullUrl = ctx -> urlBuilderService.buildFullUrl(ctx.getSource().getShortCode());


        configureShortUrlMapping(mapper, toFullUrl);

        return mapper;
    }

    private void configureShortUrlMapping(ModelMapper mapper, Converter<ShortUrl, String> toFullUrl) {
        mapper.createTypeMap(ShortUrl.class, ShortUrlDto.class)
                .addMappings(cfg -> cfg
                        .using(toFullUrl)
                        .map(src -> src, ShortUrlDto::setShortenedUrl)
                );

        mapper.createTypeMap(ShortUrl.class, ShortUrlMetadataDto.class)
                .addMappings(cfg -> cfg
                        .using(toFullUrl)
                        .map(src -> src, ShortUrlMetadataDto::setShortenedUrl)
                )
                .addMapping(ShortUrl::getOriginalUrl, ShortUrlMetadataDto::setOriginalUrl)
                .addMapping(ShortUrl::getShortCode, ShortUrlMetadataDto::setShortCode)
                .addMapping(ShortUrl::getVisitCount, ShortUrlMetadataDto::setVisitCount)
                .addMapping(ShortUrl::getCreatedDate, ShortUrlMetadataDto::setCreatedDate)
                .addMapping(ShortUrl::getLastModifiedDate, ShortUrlMetadataDto::setLastModifiedDate)
                .addMapping(ShortUrl::getCreatedBy, ShortUrlMetadataDto::setCreatedBy)
                .addMapping(ShortUrl::getLastModifiedBy, ShortUrlMetadataDto::setLastModifiedBy);
    }
}

