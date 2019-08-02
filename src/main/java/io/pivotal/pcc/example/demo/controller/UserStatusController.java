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

@RestController
@RequestMapping("/userstatus")
public class UserStatusController {

  private static Logger logger = LogManager.getLogger(UserStatusController.class);

  @Autowired
  private final UserStatusService userStatusService;

  @Autowired
  public UserStatusController(
      UserStatusService userStatusService) {
    this.userStatusService = userStatusService;
  }

  @GetMapping("/hello")
  public String sayHello(){
    logger.info("**Received request**");
    return "<h2>Key: \"HelloWorld\" cached with value: </h2>";
  }

  @GetMapping("/getUserStatus")
  public String getUserStatus(@RequestParam("id") int id){
    logger.info("** get user status with ID**");
    return "<h2>Key: \"HelloWorld\" cached with value: " + userStatusService.getUserStatus(id) + "</h2>";
  }
  @PostMapping("/updateUserStatus")
  public String updateUserStatus(@RequestParam("id") int id, @RequestParam("addStars") boolean addStars, @RequestParam("newDocRead") String newDoc){
    logger.info("**Received request**");
    return "<h2>Key: \"HelloWorld\" cached with value: " + userStatusService.updateUserStatus(id,addStars,newDoc) + "</h2>";
  }
}
