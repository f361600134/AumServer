/*
 *     Copyright 2016-2026 TinyZ
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.janlr.sanxiao.game.handler;

import io.netty.channel.ChannelHandlerContext;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.ogcs.app.ExecutorJob;
import org.ogcs.app.Session;
import org.ogcs.netty.handler.mq.LogicProcessor;
import org.ogcs.netty.handler.mq.MessageQueueHandler;

import com.google.protobuf.InvalidProtocolBufferException;
import com.janlr.sanxiao.game.generated.Gpb.Request;

public class ServerHandler2 extends MessageQueueHandler<Request> {

    private static final Logger LOG = LogManager.getLogger(ServerHandler2.class);

    public ServerHandler2(LogicProcessor processor) {
        super(processor);
    }

    @Override
    protected ExecutorJob newExecutor(Session session, Request msg) {
        return new GpbExecutor(session, msg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof IOException) {
            LOG.info("远程主机强迫关闭了一个现有的连接 : " + ctx.channel().remoteAddress().toString() + " => " + ctx.channel().localAddress().toString());
        } else if (cause.getCause() instanceof InvalidProtocolBufferException) {
            LOG.info("Invalid Google Protocol Buffer Message : " + cause.getMessage());
            super.exceptionCaught(ctx, cause);
        } else {
            super.exceptionCaught(ctx, cause);
        }
    }
}
