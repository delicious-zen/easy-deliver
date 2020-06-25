package com.delicious.easy_deliver.buyer.persistent.model

enum class OrderStatus(var value:Int){
  CREATED(1),
  DELIVERING(2),
  CANCELED(3),
  COMPLETED(4);
}