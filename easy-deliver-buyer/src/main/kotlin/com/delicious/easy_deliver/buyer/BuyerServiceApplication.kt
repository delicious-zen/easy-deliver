package com.delicious.easy_deliver.buyer

import com.delicious.easy_deliver.buyer.persistent.repository.BuyerRepository
import io.vertx.core.Vertx
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.stereotype.Component

@SpringBootApplication
open class BuyerServiceApplication

fun main(args: Array<String>) {
  SpringApplication.run(BuyerServiceApplication::class.java, *args)
}

@Component
class DeployMan(private val dao: BuyerRepository):CommandLineRunner{
  override fun run(vararg args: String?) {
    val vertx = Vertx.vertx()
    val log = LoggerFactory.getLogger(this.javaClass)

    vertx.deployVerticle(BuyerServiceVerticle(dao))
            .onComplete { ar->
              run {
                if (ar.succeeded()) log.info("deploy buyerServiceVerticle ok!")
                else log.error("deploy buyerServiceVerticle failed!", ar.cause())
              }
            }
  }

}