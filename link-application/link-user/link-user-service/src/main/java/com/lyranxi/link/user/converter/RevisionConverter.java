package com.lyranxi.link.user.converter;

import com.lyranxi.link.user.bo.RevisionCreateBO;
import com.lyranxi.link.user.bo.RevisionUpdateBO;
import com.lyranxi.link.user.entity.Revision;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

/**
 * @author ranxi
 * @date 2025-04-21 16:54
 */
@Mapper
public interface RevisionConverter {

    RevisionConverter INSTANCE = Mappers.getMapper(RevisionConverter.class);

    Revision to(RevisionCreateBO params);

    Revision to(RevisionUpdateBO params);

}
