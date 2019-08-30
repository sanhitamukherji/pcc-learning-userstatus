/*
 * Copyright (C) 2019-Present Pivotal Software, Inc. All rights reserved.
 *
 * This program and the accompanying materials are made available under
 * the terms of the under the Apache License, Version 2.0 (the "License”);
 * you may not use this file except in compliance with the License. You
 * may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed
 * under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES
 * OR CONDITIONS OF ANY KIND, either express or implied. See the License for
 * the specific language governing permissions and limitations under the License.
 */

package io.pivotal.pcc.example.demo.controller;

import io.pivotal.pcc.example.demo.service.UserStatusService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;

@Controller
public class UserStatusUIController {

  private static Logger logger = LogManager.getLogger(UserStatusUIController.class);

  private final UserStatusService userStatusService;

  @Autowired
  public UserStatusUIController(
          UserStatusService userStatusService) {
    this.userStatusService = userStatusService;
  }

  @CrossOrigin
  @GetMapping("/")
  public String ping(Model model, HttpSession httpSession){
    if ((httpSession.getAttribute("name")) !=null || httpSession.getAttribute("message") != null){
      model.addAttribute("username", "david");
      return "userLoggedIn";
    }
    logger.info("**Received request**");
    return "userNotLoggedIn";
  }

}
