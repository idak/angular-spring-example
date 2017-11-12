package com.idak.tuto.api.model

import scala.beans.BeanProperty

class User( @BeanProperty var id: Int,
            @BeanProperty var username: String,
            @BeanProperty var  password: String,
            @BeanProperty var email: String ) {
  def this() {
    this(0, "", "", "")
  }
}

