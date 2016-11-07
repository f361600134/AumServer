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

package com.janlr.sanxiao.game.command.impl;

import org.ogcs.app.Session;
import org.ogcs.okra.example.game.command.AbstractCommand;
import org.ogcs.okra.example.game.generated.Example;
import org.ogcs.okra.example.game.generated.Gpb;
import org.ogcs.okra.example.game.generated.Gpb.Request;
import org.ogcs.okra.example.game.persistence.domain.MemRole;
import org.ogcs.okra.example.game.server.Role;

/**
 * Game login command
 */
public class GAME_LOGIN extends AbstractCommand {

    @Override
    public void execute(Session session, Request request) throws Exception {
        Example.MsgLogin msgLogin = Example.MsgLogin.parseFrom(request.getData());

        // Get role by account. Also we can cache role in memory.
        MemRole memRole = roleMapper.select(msgLogin.getAccount());
//        if (!msgLogin.getPsw().equals(memRole.getPsw())) {
//            // TODO: password is wrong  验证登录
//            return;
//        }

        // TODO: do some logic content

        Role player = new Role(session, memRole);
        // session set player.
        // The player's function disconnect()  will be invoked, When the session is inactive.
        session.setConnector(player);

        session.writeAndFlush(Gpb.Response.newBuilder()
                .setId(request.getId())
                .setData(request.getData())
                .build());
    }
}
