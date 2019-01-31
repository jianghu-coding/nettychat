package com.im.nettychat.service.impl;

import com.im.nettychat.config.db.DBUtil;
import com.im.nettychat.domain.Resource;
import com.im.nettychat.domain.mapper.ResourceMapper;
import com.im.nettychat.protocol.request.SearchResourceRequest;
import com.im.nettychat.protocol.response.SearchResourceResponse;
import com.im.nettychat.proxy.CglibServiceInterceptor;
import com.im.nettychat.service.BaseService;
import com.im.nettychat.service.ResourceService;
import io.netty.channel.ChannelHandlerContext;
import org.apache.ibatis.session.SqlSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author hejianglong
 * @date 2019/1/30.
 */
public class ResourceServiceImpl extends BaseService implements ResourceService {

    public static final ResourceService resourceService = (ResourceServiceImpl) CglibServiceInterceptor.getCglibProxy(ResourceServiceImpl.class);

    @Override
    public void findValuesByType(ChannelHandlerContext ctx, SearchResourceRequest request) {
        SearchResourceResponse resourceResponse = new SearchResourceResponse();
        SqlSession sqlSession = DBUtil.getSession(true);
        ResourceMapper resourceMapper = sqlSession.getMapper(ResourceMapper.class);
        List<Resource> resourceValues =  resourceMapper.findValuesByType(request.getType());
        List<String> values = resourceValues.stream().map(r -> r.getValue()).collect(Collectors.toList());
        resourceResponse.setValues(values);
        ctx.writeAndFlush(resourceResponse);
    }
}
