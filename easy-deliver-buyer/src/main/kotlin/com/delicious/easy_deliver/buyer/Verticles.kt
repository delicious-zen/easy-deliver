package com.delicious.easy_deliver.buyer

import com.delicious.easy_deliver.buyer.persistent.GetBuyerByIdHandler
import com.delicious.easy_deliver.buyer.persistent.model.Buyer
import com.delicious.easy_deliver.buyer.persistent.repository.BuyerRepository
import io.vertx.core.AbstractVerticle
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class BuyerServiceVerticle(private val dao: BuyerRepository) : AbstractVerticle() {
  val log: Logger = LoggerFactory.getLogger(this.javaClass)
  override fun start() {
    val bus = vertx.eventBus()
    bus.consumer<Any>("buyerService/get") { GetBuyerByIdHandler(vertx, dao) }
  }
}