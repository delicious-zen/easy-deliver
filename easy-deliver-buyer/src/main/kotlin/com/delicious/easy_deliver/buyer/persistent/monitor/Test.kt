package com.delicious.easy_deliver.buyer.persistent.monitor

import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.cloud.stream.annotation.EnableBinding
import org.springframework.cloud.stream.annotation.Input
import org.springframework.cloud.stream.annotation.Output
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.core.ReactiveStringRedisTemplate
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.SubscribableChannel
import org.springframework.messaging.support.GenericMessage

@Configuration
@EnableBinding(TestSink::class, TestSource::class)
open class TestConfig(
        private val redisStringRedisTemplate: ReactiveStringRedisTemplate,
        private val sink: TestSink,
        private val source: TestSource
) : CommandLineRunner {

  val log = LoggerFactory.getLogger(this.javaClass)

  override fun run(vararg args: String?) {
    sink.input()
    source.output()
    mQTest()
    redisTest()
  }

  private fun mQTest() = run{
    sink.input().subscribe{
      log.info("接收到消息：$it")
    }
    source.output().send(GenericMessage("hello message"))
  }

  private fun redisTest() = run {
    val opsForValue = redisStringRedisTemplate.opsForValue()
    opsForValue.set("k","v")
            .doOnSuccess { log.info("redis set ok!") }
            .and { opsForValue.get("k").subscribe(log::info) }
            .subscribe()
  }
}

interface TestSink {
  @Input(INPUT)
  fun input(): SubscribableChannel

  companion object {
    const val INPUT = "test-input"
  }
}

interface TestSource {
  @Output(OUTPUT)
  fun output(): MessageChannel

  companion object {
    const val OUTPUT = "test-output"
  }
}
