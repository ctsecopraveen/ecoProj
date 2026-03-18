package com.ecotrack.config;

import com.ecotrack.entity.EmissionLog.EmissionStatus;
import com.ecotrack.entity.EmissionLog.EmissionType;
import com.ecotrack.entity.IndustryDocument.DocType;
import com.ecotrack.entity.IndustryDocument.VerificationStatus;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class StringToEnumConverter implements WebMvcConfigurer {

    @Override
    public void addFormatters(FormatterRegistry registry) {

        registry.addConverter(new Converter<String, EmissionType>() {
            @Override
            public EmissionType convert(String source) {
                for (EmissionType type : EmissionType.values()) {
                    if (type.name().equalsIgnoreCase(source.trim())) {
                        return type;
                    }
                }
                throw new IllegalArgumentException(
                        "Invalid emission type: '" + source +
                                "'. Valid values are: CO2, NOx, SOx"
                );
            }
        });

        registry.addConverter(new Converter<String, EmissionStatus>() {
            @Override
            public EmissionStatus convert(String source) {
                try {
                    return EmissionStatus.valueOf(source.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Invalid emission status: '" + source +
                                    "'. Valid values are: PENDING, APPROVED, REJECTED, FLAGGED"
                    );
                }
            }
        });

        registry.addConverter(new Converter<String, DocType>() {
            @Override
            public DocType convert(String source) {
                try {
                    return DocType.valueOf(source.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Invalid doc type: '" + source +
                                    "'. Valid values are: PERMIT, COMPLIANCE"
                    );
                }
            }
        });

        registry.addConverter(new Converter<String, VerificationStatus>() {
            @Override
            public VerificationStatus convert(String source) {
                try {
                    return VerificationStatus.valueOf(source.trim().toUpperCase());
                } catch (IllegalArgumentException e) {
                    throw new IllegalArgumentException(
                            "Invalid verification status: '" + source +
                                    "'. Valid values are: PENDING, VERIFIED, REJECTED"
                    );
                }
            }
        });
    }
}