package com.idak.tuto.api.resource

import com.idak.tuto.api.model.User
import com.idak.tuto.api.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation._

@RestController
@RequestMapping(path = Array("/users"))
class UserResource(userService: UserService) {

  @GetMapping
  def get = userService.get

  @GetMapping(path = Array("/{id}"))
  def get(@PathVariable id: Int) : User = userService.getOne(id).get

  @PostMapping
  def add(@RequestBody user: User) : User = userService.create(user)

  @DeleteMapping(Array("/{id}"))
  @ResponseStatus(HttpStatus.NO_CONTENT)
  def delete(@PathVariable id: Int) = userService.delete(id)

}
