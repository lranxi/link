package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.SignupBO;
import com.lyranxi.link.user.entity.User;
import javax.annotation.processing.Generated;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-03-17T21:25:31+0800",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 21.0.6 (Azul Systems, Inc.)"
)
public class SignupConverterImpl implements SignupConverter {

    @Override
    public User toEntity(SignupBO params) {
        if ( params == null ) {
            return null;
        }

        User user = new User();

        user.setTenantId( params.getTenantId() );
        user.setStoreId( params.getStoreId() );
        user.setUsername( params.getUsername() );
        user.setNickname( params.getNickname() );
        user.setAvatar( params.getAvatar() );
        user.setPhone( params.getPhone() );

        return user;
    }
}
