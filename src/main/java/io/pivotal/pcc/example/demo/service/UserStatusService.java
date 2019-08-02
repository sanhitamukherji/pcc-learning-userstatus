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

package io.pivotal.pcc.example.demo.service;

import io.pivotal.pcc.example.demo.model.UserStatus;
import io.pivotal.pcc.example.demo.repository.UserStatusRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class UserStatusService {

  @Autowired
  private UserStatusRepository userStatusRepository;

  private static Logger logger = LogManager.getLogger(UserStatusService.class);

  @Cacheable("UserStatus")
  public String sayHelloWorld(String helloString){
    logger.info("************Hitting the service");
    return String.valueOf(System.nanoTime());
  }

  public UserStatus getUserStatus(int id){
    logger.info("** get user status with ID**");
    return this.userStatusRepository.findById(id).orElse(null);
  }

  public UserStatus updateUserStatus(int id, boolean addStars,String newDoc){
    logger.info("** Adding new doc to read docs list and/or new stars to user**");
    UserStatus user=userStatusRepository.findById(id).orElse(new UserStatus(id));
    if(addStars)
      user.addStar();
    user.addNewDoc(newDoc);
    return this.userStatusRepository.save(user);
  }
}
