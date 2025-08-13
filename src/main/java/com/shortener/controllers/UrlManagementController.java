package com.shortener.controllers;


import com.shortener.models.ShortUrl;
import com.shortener.models.dto.ShortUrlDto;
import com.shortener.models.dto.ShortUrlMetadataDto;
import com.shortener.models.dto.ShortenRequest;
import com.shortener.models.ShortenerUser;
import com.shortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.Explode;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.ParameterStyle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/urls")
public class UrlManagementController {

    private final UrlShortenerService urlService;
    private final ModelMapper modelMapper;
    public UrlManagementController(UrlShortenerService urlService,
                         ModelMapper modelMapper) {
        this.urlService = urlService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ShortUrlDto> shortenUrl(@Valid @RequestBody ShortenRequest request,
                                               @AuthenticationPrincipal ShortenerUser currentUser) {
        ShortUrl shortUrl = urlService.shorten(request.getOriginalUrl(),currentUser);
        return new ResponseEntity<>(modelMapper.map(shortUrl,ShortUrlDto.class), HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<Page<ShortUrlMetadataDto>> getUserUrlsMetadata(
            @ParameterObject
            @Parameter(
                    name = "sort",
                    in = ParameterIn.QUERY,
                    description = "Sort criteria, e.g., property,asc or property,desc; multiple supported.",
                    schema = @Schema(type = "string", example = "createdDate,desc"),
                    style = ParameterStyle.FORM,
                    explode = Explode.FALSE
            )
            Pageable pageable,
            @AuthenticationPrincipal(expression = "username") String email
    ) {
        Page<ShortUrl> urls = urlService.findAllByUrlsOwnerEmail(email, pageable);
        Page<ShortUrlMetadataDto> metadataDtos = urls.map(url ->
                modelMapper.map(url, ShortUrlMetadataDto.class)
        );
        return ResponseEntity.ok(metadataDtos);
    }


}
