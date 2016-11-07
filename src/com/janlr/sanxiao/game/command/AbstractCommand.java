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

package com.janlr.sanxiao.game.command;

import org.ogcs.app.AppContext;
import org.ogcs.app.Command;
import org.ogcs.app.Session;
import org.ogcs.okra.example.game.generated.Gpb;
import org.ogcs.okra.example.game.persistence.mapper.RoleMapper;
import org.ogcs.okra.example.game.server.SpringContext;

public abstract class AbstractCommand implements Command<Session, Gpb.Request> {

    protected RoleMapper roleMapper = (RoleMapper) AppContext.getBean(SpringContext.MAPPER_ROLE);

}
