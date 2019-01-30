package com.im.nettychat.service.impl;

import com.im.nettychat.config.db.DBUtil;
import com.im.nettychat.domain.mapper.ResourceMapper;
import com.im.nettychat.protocol.request.ResourceRequest;
import com.im.nettychat.protocol.response.ResourceResponse;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.service.ResourceService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.session.SqlSession;
import java.util.List;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public class ResourceServiceImpl implements ResourceService {

    public static final ResourceService resourceService = (ResourceServiceImpl) CglibServiceInterceptor.getCglibProxy(ResourceServiceImpl.class);

    @Override
    public void findValuesByType(ChannelHandlerContext ctx, ResourceRequest request) {
        ResourceResponse resourceResponse = new ResourceResponse();
        SqlSession sqlSession = DBUtil.getSession(true);
        ResourceMapper resourceMapper = sqlSession.getMapper(ResourceMapper.class);
        List<String> values =  resourceMapper.findValuesByType(request.getType());
        resourceResponse.setValues(values);
        ctx.writeAndFlush(resourceResponse);
    }
}
