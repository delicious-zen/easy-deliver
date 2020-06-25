package com.delicious.easy_deliver.buyer.persistent

import com.delicious.easy_deliver.buyer.persistent.model.Buyer
import com.delicious.easy_deliver.buyer.persistent.repository.BuyerRepository
import io.vertx.core.Handler
import io.vertx.core.Vertx
import io.vertx.core.eventbus.Message
import kotlinx.serialization.UnstableDefault
import kotlinx.serialization.json.Json
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GetBuyerByIdHandler(private val vertx: Vertx, private val dao: BuyerRepository)
  : Handler<Message<Any>> {

  private val log: Logger = LoggerFactory.getLogger(this.javaClass)



  @OptIn(UnstableDefault::class)
  override fun handle(request: Message<Any>) {
    val id = request.body() as Long


    vertx.executeBlocking<Any>(

            {
              promise ->
              val buyer = dao.getOne(id)
              promise.complete(buyer)
            },

            { res ->
              run {
                if (res.succeeded()) {
                  val jsonRes = "not implement yet"
                  log.info("get res from database. reply...")
                  request.reply(jsonRes)
                }
              }
            }
    )

  }

}