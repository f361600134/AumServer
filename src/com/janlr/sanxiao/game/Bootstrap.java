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

package com.janlr.sanxiao.game;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Bootstrap game server
 */
public class Bootstrap {

    private static final Logger LOG = LogManager.getLogger(Bootstrap.class);

    public static void main(String[] args) {
        LOG.info("PreBootstrap server.");
        ClassPathXmlApplicationContext context = null;
        try {
            context = new ClassPathXmlApplicationContext("classpath:spring/beans.xml");
            context.registerShutdownHook();
            LOG.info("Server bootstrap successful.");
        } catch (Exception e) {
            if (context != null)
                context.close();
            LOG.error("Server bootstrap failure.", e);
        }
    }
}
