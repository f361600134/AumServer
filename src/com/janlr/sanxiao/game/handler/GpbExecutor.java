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

import org.ogcs.app.Command;
import org.ogcs.app.ExecutorJob;
import org.ogcs.app.Session;

import com.janlr.sanxiao.game.command.Commands;
import com.janlr.sanxiao.game.generated.Gpb;
import com.janlr.sanxiao.game.generated.Gpb.Request;

public class GpbExecutor implements ExecutorJob {

    protected Session session;
    protected Request request;

    public GpbExecutor(Session session, Request request) {
        this.session = session;
        this.request = request;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void onExecute() {
        if (null == request) {
            throw new NullPointerException("request");
        }
        int api = request.getApi();
        if (!isLogin(session) && !Commands.INSTANCE.isCmdWithoutAuth(api)) {
            // TODO: custom wrapper to make reply easy.
            session.writeAndFlush(Gpb.Response.newBuilder()
                    .setId(request.getId())
                    .setError(Gpb.Error.newBuilder()
                            .setRet(-100)
                            .build())
                    .build());
            return;
        }
        try {
            Command command = Commands.INSTANCE.interpret(api);
            if (command != null) {
                command.execute(session, request);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isLogin(Session session) {
        return session.getConnector() != null;
    }

    @Override
    public void release() {
        this.session = null;
        this.request = null;
    }
}
