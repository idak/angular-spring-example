package com.idak.tuto.api.service

import com.idak.tuto.api.model.User
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

import scala.collection.mutable.ListBuffer

@Service
class UserService(passwordEncoder: PasswordEncoder) {
  var users = new ListBuffer[User]
  users += new User(generateId,"admin",passwordEncoder.encode("admin"),"admin@gmail.com")
  users += new User(generateId,"user",passwordEncoder.encode("user"),"user@gmail.com")



  def create(user: User): User = {
    user.setId(generateId)
    user.setPassword(passwordEncoder.encode(user.getPassword))
    users += user
    user
  }

  def delete(id: Int) = {
    users --= users.filter(_.getId == id)
  }

  def get = users.toArray

  def getOne(id: Int) : Option[User] = users.find(_.getId == id)

  def authenticate(username: String, password: String) : Option[User] = {
    users.find(u => u.getUsername.equals(username) && passwordEncoder.matches(password, u.getPassword))
  }

  private def generateId : Int = {
    if (users.isEmpty) 1 else users.map(_.getId).max + 1
  }


}
